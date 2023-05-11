<?php

namespace App\Controller;

use App\Entity\Produit;
use App\Entity\Categorie;
use App\Entity\User;
use App\Entity\Favori;
use App\Form\ProduitType;
use Symfony\Component\Serializer\SerializerInterface;
use App\Repository\FavoriRepository;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use App\Repository\ProduitRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Serializer\Annotation\Groups;

use Symfony\Component\Serializer\Normalizer\NormalizerInterface;


#[Route('/produit')]
class ProduitController extends AbstractController
{
    #[Route('/', name: 'app_produit_index', methods: ['GET', 'POST'])]
public function index(ProduitRepository $produitRepository, Request $request): Response
{
    $categories = $this->getDoctrine()->getRepository(Categorie::class)->findAll();
    $selectedCategoryId = $request->query->get('category');

    $produits = null;
    $selectedCategory = null;
    if ($selectedCategoryId) {
        $selectedCategory = $this->getDoctrine()->getRepository(Categorie::class)->find($selectedCategoryId);
        $produits = $produitRepository->findBy(['id_categorie' => $selectedCategory]);
    } else {
        $produits = $produitRepository->findAll();
    }
    

    $form = $this->createFormBuilder()
        ->add('category', EntityType::class, [
            'class' => Categorie::class,
            'choice_label' => 'nom_categorie',
            'required' => false,
            'placeholder' => 'Toutes les catégories',
            'data' => $selectedCategory,
        ])
        ->add('submit', SubmitType::class, [
            'label' => 'Filtrer',
        ])
        ->setMethod('GET')
        ->getForm();

    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $data = $form->getData();
        $selectedCategory = $data['category'];
        if ($selectedCategory) {
            return $this->redirectToRoute('app_produit_index', ['category' => $selectedCategory->getIdCategorie()]);
        } else {
            return $this->redirectToRoute('app_produit_index');
        }
    }
    

    return $this->render('produit/index.html.twig', [
        'produits' => $produits,
        'categories' => $categories,
        'selected_category' => $selectedCategory,
        'form' => $form->createView(),
    ]);
}




    #[Route('/produitbyuser', name: 'app_produit_produitbyuser', methods: ['GET'])]
    public function produitbyuser(ProduitRepository $produitRepository): Response
    {
        $user = $this->getDoctrine()->getRepository(User::class)->find(3);
        return $this->render('produit/produitbyuser.html.twig', [
            'produits' => $produitRepository->findProduitByUser($user),
        ]);
    }



    #[Route('/produitback', name: 'app_produit_indexback', methods: ['GET'])]
    public function indexback(ProduitRepository $produitRepository): Response
    {
        return $this->render('produit/indexback.html.twig', [
            'produits' => $produitRepository->findAll(),
        ]);
    }

   


    

    #[Route('/new', name: 'app_produit_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ProduitRepository $produitRepository, SluggerInterface $slugger): Response
    {
        $produit = new Produit();
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $user = $this->getDoctrine()->getRepository(User::class)->find(21);
            $produit->setIdUser($user);
            $photo = $form->get('photo')->getData();
           

            // this condition is needed because the 'brochure' field is not required
            // so the PDF file must be processed only when a file is uploaded
            if ($photo) {
                $originalFilename = pathinfo($photo->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$photo->guessExtension();

                // Move the file to the directory where brochures are stored
                try {
                    $photo->move(
                        $this->getParameter('produit_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // updates the 'brochureFilename' property to store the PDF file name
                // instead of its contents
                $produit->setImageProduit($newFilename);
            }
            $produitRepository->save($produit, true);
           


            return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('produit/new.html.twig', [
            'produit' => $produit,
            'form' => $form,
        ]);
    }

    #[Route('/{id_produit}', name: 'app_produit_show', methods: ['GET'])]
    public function show(Produit $produit): Response
    {
        return $this->render('produit/show.html.twig', [
            'produit' => $produit,
        ]);
    }

    #[Route('/{id_produit}/edit', name: 'app_produit_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Produit $produit, ProduitRepository $produitRepository, SluggerInterface $slugger): Response
    {
        $form = $this->createForm(ProduitType::class, $produit);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $photo = $form->get('photo')->getData();
            if ($photo) {
                $originalFilename = pathinfo($photo->getClientOriginalName(), PATHINFO_FILENAME);
                // this is needed to safely include the file name as part of the URL
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'-'.uniqid().'.'.$photo->guessExtension();

                // Move the file to the directory where brochures are stored
                try {
                    $photo->move(
                        $this->getParameter('produit_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // ... handle exception if something happens during file upload
                }

                // updates the 'brochureFilename' property to store the PDF file name
                // instead of its contents
                $produit->setImageProduit($newFilename);
            }
            $produitRepository->save($produit, true);

            return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('produit/edit.html.twig', [
            'produit' => $produit,
            'form' => $form,
        ]);
    }

    #[Route('/{id_produit}', name: 'app_produit_delete', methods: ['POST'])]
    public function delete(Request $request, Produit $produit, ProduitRepository $produitRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$produit->getIdProduit(), $request->request->get('_token'))) {
            $produitRepository->remove($produit, true);
        }

        return $this->redirectToRoute('app_produit_index', [], Response::HTTP_SEE_OTHER);
    }
   /* #[Route('/{id}', name: 'app_produit_showbyid', methods: ['GET'])]
public function showbyid(Produit $produit): Response
{
    return $this->render('produit/showbyid.html.twig', [
        'produit' => $produit,
    ]);
}*/
    #[Route('/show/{id_produit/}', name: 'app_produit_showbyid', methods: ['GET'])]
    public function showProductsByUserId($userId)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $user = $entityManager->getRepository(User::class)->find($userId);

        if (!$user) {
            throw $this->createNotFoundException(
                'No user found for id '.$userId
            );
        }

        $products = $user->getProducts();

        return $this->render('products_by_user.html.twig', [
            'user' => $user,
            'products' => $products,
    ]);
}
#[Route('/products/{id}/toggle-favorite', name:'toggle_favorite')]
     
    public function toggleFavorite(Request $request, Produit $produit, FavoriRepository $favoriRepository): JsonResponse
    {
        
        $user = $this->getDoctrine()->getRepository(User::class)->find(2);
        $favori = $favoriRepository->findOneBy(['produit' => $produit, 'user' => $user]);
        if (!$favori) {
            $favori = new Favori();
            $favori->setProduit($produit);
            $user = $this->getDoctrine()->getRepository(User::class)->find(2);
            $favori->setUser($user);
            $favoriRepository->save($favori, true);

            return new JsonResponse(['status' => 'success', 'favorite' => true]);
        }

        $favoriRepository->remove($favori, true);

        return new JsonResponse(['status' => 'success', 'favorite' => false]);
    }

           
    
    #[Route('/statistic/stats', name:"statistic")]
        public function statistic(ProduitRepository $produitRepository): Response
    {
        $categories = $produitRepository->getCategoryStatistics();

        $chartData = [];
        $chartLabels = [];

        foreach ($categories as $category) {
            $chartLabels[] = $category['nom_categorie'];
            $chartData[] = $category['count'];
        }

        return $this->render('produit/statistic.html.twig', [
            'chartData' => $chartData,
            'chartLabels' => $chartLabels,
        ]);
    }


    #[Route('/chart/stat', name: 'produit_chart')]
    public function chart(SerializerInterface $serializer): Response
    {
        $produits = $this->getDoctrine()->getRepository(Produit::class)->findAll();

        $data = [];
        foreach ($produits as $produit) {
            $data[] = [
                'nom_produit' => $produit->getNomProduit(),
                'prix_produit' => $produit->getPrixProduit(),
            ];
        }

        $json = $serializer->serialize($data, JsonEncoder::FORMAT);

        return $this->render('produit/chart.html.twig', [
            'chartData' => $json,
        ]);
    }

    #[Route('/statistics/favori', name: 'statistics')]
    public function statistics(): Response
    {
        $em = $this->getDoctrine()->getManager();

        // Get all products
        $produits = $em->getRepository(Produit::class)->findAll();

        // Get all users
        $users = $em->getRepository(User::class)->findAll();

        // Count the number of favoris for each product
        $favorisCount = [];
        foreach ($produits as $produit) {
            $favorisCount[$produit->getNomProduit()] = count($produit->getFavoris());
        }

        // Count the number of favoris for each user
        $userFavorisCount = [];
        foreach ($users as $user) {
            $userFavorisCount[$user->getUsername()] = count($user->getFavoris());
        }

        // Get the top 5 products with the most favoris
        arsort($favorisCount);
        $topProducts = array_slice($favorisCount, 0, 5, true);

        // Get the top 5 users with the most favoris
        arsort($userFavorisCount);
        $topUsers = array_slice($userFavorisCount, 0, 5, true);

        // Prepare data for chart.js
        $chartData = [
            'products' => [
                'labels' => array_keys($topProducts),
                'data' => array_values($topProducts),
            ],
            'users' => [
                'labels' => array_keys($topUsers),
                'data' => array_values($topUsers),
            ],
        ];

        $serializer = $this->container->get('serializer');
        $chartDataJson = $serializer->serialize($chartData, JsonEncoder::FORMAT);

        return $this->render('produit/favoristat.html.twig0', [
            'chartDataJson' => $chartDataJson,
        ]);
    }


    
    #[Route('/showjson/json', name: 'listProduitjson')]
public function readProduitsjson(NormalizerInterface $normalizer): Response
{
    $r = $this->getDoctrine()->getRepository(Produit::class);

    
    $produits = $r->findAll();

    // Transform the entities into an array of data
   

     $commandesNormalises = $normalizer->normalize( $produits, 'json', ['groups' => "produits"]);

    $json = json_encode($commandesNormalises);

    return new Response($json);
}

}
