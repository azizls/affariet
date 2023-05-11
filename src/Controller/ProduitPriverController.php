<?php

namespace App\Controller;
use App\Entity\ProduitPriver;
use App\Form\ProduitPriverType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\User;
use App\Repository\UserRepository;
use Doctrine\Persistence\ManagerRegistry;
use App\Repository\ProduitPriverRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Security\Core\Security;


class ProduitPriverController extends AbstractController
{
    #[Route('/produit/priver', name: 'app_produit_priver')]
    public function index(): Response
    {
        return $this->render('produit_priver/addProduitpriver.html.twig', [
            'controller_name' => 'ProduitPriverController',
        ]);
    }


    #[Route('/produit-priver/add', name: 'app_produit_priver_add')]
    public function addProduitPriver(Request $request, Security $security)
    {
        $produitPriver = new ProduitPriver();
        $form = $this->createForm(ProduitPriverType::class, $produitPriver);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // Get the user from the database
            $user = $security->getUser();
            // Set the user of the produitPriver entity
            $produitPriver->setIduser($user);
            
            // Handle file upload
            /** @var UploadedFile $imageFile */
            $imageFile = $form->get('image')->getData();

            if ($imageFile) {
                $newFilename = uniqid().'.'.$imageFile->guessExtension();

                try {
                    $imageFile->move(
                        $this->getParameter('produit_priver_images_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // Handle exception if something went wrong during file upload
                }

                $produitPriver->setImage($newFilename);
            }
            $produitPriver->setEtat('disponible');
            // Save the produitPriver entity
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($produitPriver);
            $entityManager->flush();
            
            return $this->redirectToRoute('app_readProduitPriver');
        }

        return $this->render('produit_priver/addProduitpriver.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    #[Route('/produit-priver/show', name: 'app_readProduitPriver')]
    public function readProduitPriver(PaginatorInterface $paginator,Request $request,Security $security): Response
    {

        $r = $this->getDoctrine()->getRepository(ProduitPriver::class);
        //Utiliser findAll()
        $user = $security->getUser();
        $produits = $r->findProduitPrivesByUser($user);
        $pagination = $paginator->paginate(
            $produits,
            $request->query->getInt('page', 1), 8
        );
        dump($produits); // <-- add this line to debug

        return $this->render('produit_priver/show.html.twig', [
            'c' => $pagination,
            
        ]);
    }

    public function searchProduitsPrives(Request $request)
{
    $query = $request->request->get('query');

    $produits = $this->getDoctrine()->getRepository(ProduitPriver::class)->findByNom($query);

    return $this->render('partials/search_results.html.twig', [
        'produits' => $produits,
    ]);
}



    #[Route('/produit-priver/affciherPP/{id}', name: 'app_readProduitPriverByID')]
    public function readProduitPriverByid($id): Response
    {
        $r = $this->getDoctrine()->getRepository(ProduitPriver::class);
        //Utiliser findAll()
       
        $produits = $r->find($id);

        dump($produits); // <-- add this line to debug

        return $this->render('produit_priver/affciherPP.html.twig', [
            'produit' => $produits,
        ]);
    }
    #[Route('/produit-priver/affciherPPView/{id}/{iduser}', name: 'app_readProduitPriverByIDView')]
    public function readProduitPriverByidView($id,$iduser): Response
    {
        $r = $this->getDoctrine()->getRepository(ProduitPriver::class);
        //Utiliser findAll()
       
        $produits = $r->find($id);

        dump($produits); // <-- add this line to debug

        return $this->render('produit_priver/affciherPPView.html.twig', [
            'produit' => $produits,
            'Sender' => $iduser,
        ]);
    }
    #[Route('/deleteP/{id}', name: 'app_deletePP')]
    public function deleteP($id,  ProduitPriverRepository $rep, 
    ManagerRegistry $doctrine): Response
    {
        //récupérer la classe à supprimer
        $produitPriver=$rep->find($id);
        if (!$produitPriver) {
            throw $this->createNotFoundException('Object not found');
        }
        $oldFilename = $produitPriver->getImage();
            if ($oldFilename) {
                $oldFilePath = $this->getParameter('produit_priver_images_directory') . '/' . $oldFilename;
                if (file_exists($oldFilePath)) {
                    unlink($oldFilePath);
                }
            }
        //Action de suppression
        //récupérer l'Entitye manager
        $em=$doctrine->getManager();
        $em->remove($produitPriver);
        //La maj au niveau de la bd
        $em->flush();
        if($produitPriver->getEtat()=='utiliser'){
            $this->addFlash('error', 'La proposition echange que vous avez affecter avec ce produit a ete suprimer');
            return $this->redirectToRoute('app_readProduitPriver');
        }
        else{
        return $this->redirectToRoute('app_readProduitPriver');
        }
    }
    
    #[Route('/updateP/{id}', name: 'app_updateProduitPriver')]
public function updateP($id, ProduitPriverRepository $rep, ManagerRegistry $doctrine, Request $request,Security $security)
{
    // Get the produitPriver entity to update
    $produitPriver = $rep->find($id);

    // Create the form with the produitPriver entity
    $form = $this->createForm(ProduitPriverType::class, $produitPriver);

    // Handle the form submission
    $form->handleRequest($request);
    if ($form->isSubmitted() && $form->isValid()) {
        // Get the user from the database
        $user = $security->getUser();

        // Set the user of the produitPriver entity
        $produitPriver->setIduser($user);

        // Handle file upload
        /** @var UploadedFile $imageFile */
        $imageFile = $form->get('image')->getData();

        if ($imageFile) {
            $newFilename = uniqid() . '.' . $imageFile->guessExtension();

            try {
                $imageFile->move(
                    $this->getParameter('produit_priver_images_directory'),
                    $newFilename
                );
            } catch (FileException $e) {
                // Handle exception if something went wrong during file upload
            }

            // Remove the old image file
            $oldFilename = $produitPriver->getImage();
            if ($oldFilename) {
                $oldFilePath = $this->getParameter('produit_priver_images_directory') . '/' . $oldFilename;
                if (file_exists($oldFilePath)) {
                    unlink($oldFilePath);
                }
            }

            $produitPriver->setImage($newFilename);
        }

        // Save the produitPriver entity
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($produitPriver);
        $entityManager->flush();

        return $this->redirectToRoute('app_readProduitPriver');
    }

    return $this->render('produit_priver/addProduitpriver.html.twig', [
        'form' => $form->createView(),
    ]);
}

}
