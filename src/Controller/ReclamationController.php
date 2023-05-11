<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Form\ReclamationType;
use App\Form\TypeReclamation;
use Symfony\Component\Serializer\Normalizer\NormalizableInterface;
use App\Repository\ReclamationRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

use Symfony\Component\HttpFoundation\JsonResponse;


#[Route('/reclamation')]
class ReclamationController extends AbstractController
{
    #[Route('/', name: 'app_reclamation_index', methods: ['GET'])]
    public function index(ReclamationRepository $reclamationRepository): Response
    {
        return $this->render('reclamation/index.html.twig', [
            'reclamations' => $reclamationRepository->findAll(),
        ]);
    }

    #[Route("addreclamationJSON", name: "addreclamationJSON")]
    public function addreclamationJSON(Request $req, NormalizerInterface $normalizer, EntityManagerInterface $em)
    {
        $reclamation = new Reclamation();
        $reclamation->setDescription($req->get('description'));
        $reclamation->setEtatReclamation($req->get('etatReclamation'));
        $reclamation->setAvis($req->get('avis'));
        $reclamation->setTypeReclamation($req->get('typeReclamation'));
    
    
    
        $em->persist($reclamation);
        $em->flush();
        
        $jsonContent = $normalizer->normalize($reclamation, 'json', ['groups' => 'reclamation']);
        return new JsonResponse($jsonContent);
    }

    #[Route("/allreclamations", name: "list")]
    public function getreclamations(ReclamationRepository $reclamationRepository, NormalizerInterface $Normalizer)
    {
        $reclamation = $reclamationRepository->findAll();
        $reclamationNormalises=$Normalizer->normalize($reclamation,'json',['groups'=> "reclamation"]); 
          
        $json =json_encode($reclamationNormalises);
    
    
        
     return new Response($json);
    }


    #[Route('/new', name: 'app_reclamation_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ReclamationRepository $reclamationRepository): Response
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $reclamationRepository->save($reclamation, true);

            return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reclamation/new.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form,
        ]);
    }

    #[Route('/{id}/rec', name: 'app_reclamation_show', methods: ['GET'])]
    public function show(Reclamation $reclamation): Response
    {
        return $this->render('reclamation/show.html.twig', [
            'reclamation' => $reclamation,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_reclamation_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Reclamation $reclamation, ReclamationRepository $reclamationRepository): Response
    {
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $reclamationRepository->save($reclamation, true);

            return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reclamation/edit.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_reclamation_delete', methods: ['POST'])]
    public function delete(Request $request, Reclamation $reclamation, ReclamationRepository $reclamationRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reclamation->getId(), $request->request->get('_token'))) {
            $reclamationRepository->remove($reclamation, true);
        }

        return $this->redirectToRoute('app_reclamation_index', [], Response::HTTP_SEE_OTHER);
    }

   
    #[Route('/registerjson/new', name: 'app_registerjson')]
        public function registerjson(Request $req,EntityManagerInterface $em, NormalizerInterface $Normalizer): Response
        {
            $reclamation = new Reclamation();
            $reclamation->setDescription($req->get('description'));
            $reclamation->setEtatReclamation($req->get('etatReclamation'));
            $reclamation->setAvis($req->get('avis'));
            //$reclamation->setTypeReclamation($req->get('typeReclamation'));;
         
             
                $em->persist($reclamation);
                $em->flush();
            // Return JSON response
                $jsonContent = $Normalizer->normalize($reclamation,'json',['groups'=>'reclamation']);
                return new Response(json_encode($jsonContent));
            }
    
    
}