<?php

namespace App\Controller;

use App\Entity\Evenement;
use App\Form\EvenementType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\BinaryFileResponse;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Component\Mercure\HubInterface;
use Symfony\Component\Mercure\Update;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\HttpFoundation\JsonResponse;






#[Route('/evenement')]
class EvenementController extends AbstractController
{
    #[Route('/back', name: 'app_evenement_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $evenements = $entityManager
            ->getRepository(Evenement::class)
            ->findAll();

        return $this->render('evenement/index.html.twig', [
            'evenements' => $evenements,
        ]);
    }
    #[Route('/', name: 'app_evenement_front_index', methods: ['GET'])]
    public function front(EntityManagerInterface $entityManager,PaginatorInterface $paginator,Request $request): Response
    {
        $evenements = $entityManager
            ->getRepository(Evenement::class)
            ->findAll();
        $pagination = $paginator->paginate(
            $evenements,
            $request->query->getInt('page', 1), 3
            );    

        return $this->render('evenement/front_index.html.twig', [
            'pagination' => $pagination,
        ]);
    }

    #[Route('/back/new', name: 'app_evenement_new', methods: ['GET', 'POST'])]
public function new(Request $request, EntityManagerInterface $entityManager): Response
{
    $evenement = new Evenement();
    $form = $this->createForm(EvenementType::class, $evenement);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        // Récupération de l'image depuis le formulaire
        $imageFile = $form->get('affiche')->getData();

        // Si une image a été ajoutée
        if ($imageFile) {
            // Génération d'un nouveau nom de fichier unique pour éviter les doublons
            $newFilename = uniqid().'.'.$imageFile->guessExtension();

            // Déplacement de l'image vers le dossier d'upload des images d'événements
            $imageFile->move(
                $this->getParameter('event_images_directory'),
                $newFilename
            );

            // Enregistrement du nom de l'image dans l'entité
            $evenement->setAffiche($newFilename);
        }

        // Ajouter la localisation à l'objet Event
        $localisation = $form->get('emplacement')->getData();
        if ($localisation) {
            $evenement->setEmplacement($localisation);
        }

        $randomColor = sprintf('#%06X', mt_rand(0, 0xFFFFFF));
        $evenement->setRandom($randomColor);
        $entityManager->persist($evenement);
        $entityManager->flush();

        return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
    }

    return $this->renderForm('evenement/new.html.twig', [
        'evenement' => $evenement,
        'form' => $form,
    ]);
}

#[Route('/back/{idEvent}', name: 'app_evenement_show', methods: ['GET'])]
public function show(Evenement $evenement): Response
{

    return $this->render('evenement/show.html.twig', [
        'evenement' => $evenement
    ]);
}
#[Route('/{idEvent}', name: 'app_evenement_front_show', methods: ['GET'])]
public function show_front(Evenement $evenement,EntityManagerInterface $entityManager): Response
{
    $evenement->setNbrView($evenement->getNbrView() + 1);
    $entityManager->persist($evenement);
    $entityManager->flush();
    return $this->render('evenement/front_show.html.twig', [
        'evenement' => $evenement
    ]);
}

    #[Route('/back/{idEvent}/edit', name: 'app_evenement_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);
         $localisation = $form->get('emplacement')->getData();
        if ($form->isSubmitted() && $form->isValid()) {

            if ($localisation) {
                $evenement->setEmplacement($localisation);
            }
            // Handle file upload
        /** @var UploadedFile $imageFile */
        $imageFile = $form->get('affiche')->getData();

        if ($imageFile) {
            $newFilename = uniqid() . '.' . $imageFile->guessExtension();

            try {
                $imageFile->move(
                    $this->getParameter('event_images_directory'),
                    $newFilename
                );
            } catch (FileException $e) {
                // Handle exception if something went wrong during file upload
            }

            // Remove the old image file
            $oldFilename = $evenement->getAffiche();
            if ($oldFilename) {
                $oldFilePath = $this->getParameter('event_images_directory') . '/' . $oldFilename;
                if (file_exists($oldFilePath)) {
                    unlink($oldFilePath);
                }
            }

            $evenement->setAffiche($newFilename);  
            $entityManager->persist($evenement);
            $entityManager->flush();  
        }
            return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('evenement/edit.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }
    #[Route('/{idEvent}/edit', name: 'app_evenement_front_edit', methods: ['GET', 'POST'])]
    public function front_edit(Request $request, Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_evenement_front_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('evenement/front_edit.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }

    #[Route('/back/{idEvent}', name: 'app_evenement_delete', methods: ['POST'])]
    public function delete(Request $request, Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
       // if ($this->isCsrfTokenValid('delete'.$evenement->getIdEvent(), $request->request->get('_token'))) {
            // Récupération des réservations associées à l'événement
         //   $reservations = $reservationRepository->findBy(['idEvent' => $evenement]);
    
            // Envoi d'une notification à chaque utilisateur
           // foreach ($reservations as $reservation) {
             //   $user = $reservation->getIdUser1();
               // $notificationService->sendNotification($user, "L'événement ".$evenement->getTitre()." a été supprimé.");
            
    
            $entityManager->remove($evenement);
            $entityManager->flush();
        
    
        return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
    }
    

    #[Route('//{idEvent}', name: 'app_evenement_front_delete', methods: ['POST'])]
    public function front_delete(Request $request, Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$evenement->getIdEvent(), $request->request->get('_token'))) {
            $entityManager->remove($evenement);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_evenement_front_index', [], Response::HTTP_SEE_OTHER);
    }

  /**
     * @Route("/cal/cale", name="calendar_index", methods={"GET"})
     */
    public function calander(EntityManagerInterface $entityManager)
    {
    
        $entityManager = $this->getDoctrine()->getManager();
    $userId = 1;
    $events = $entityManager->createQuery(
        'SELECT r, e
        FROM App\Entity\Reservation r
        JOIN r.idEvent e
        WHERE r.idUser1 = :userId'
    )->setParameter('userId', $userId)
    ->getResult();

        $rdvs = [];

        foreach($events as $event){
            $rdvs[] = [
                'id' => $event->getIdEvent()->getIdEvent(),
                'start' => $event->getIdEvent()->getDateDebut()->format('Y-m-d H:i:s'),
                'end' => $event->getIdEvent()->getDateFin()->format('Y-m-d H:i:s'),
                'title' => $event->getIdEvent()->getNomEvent(),
                'description' => $event->getIdEvent()->getDescriptionEvent(),
                'backgroundColor' => $event->getIdEvent()->getRandom()
            ];
        }

        $data = json_encode($rdvs);

        return $this->render('calendrier.html.twig', compact('data'));
    }





    #[Route('/listEvents/aa', name: 'list_event1')]
    public function listEvents(EntityManagerInterface $entityManager, SerializerInterface $serializerintertface): Response
    {
        $events = $entityManager
            ->getRepository(Evenement::class)
            ->findAll();
        $serializedData = $serializerintertface->serialize($events,'json',['groups' => 'events']);
        return new Response($serializedData, 300, [
            'Content-Type' => 'application/json'
        ]);
       
    }

    
    #[Route('/listEvents/{nomEvent}', name: 'list_event2', methods: ['GET'])]
public function listEventsNom($nomEvent, EntityManagerInterface $entityManager, SerializerInterface $serializerintertface): Response
{
    $repo = $entityManager->getRepository(Evenement::class);
    $qb = $repo->createQueryBuilder('e');
    $qb->where('e.nomEvent LIKE :name')
       ->setParameter('name', '%'.$nomEvent.'%');
    $results = $qb->getQuery()->getResult();
    $serializedData = $serializerintertface->serialize($results,'json',['groups' => 'events']);
    return new Response($serializedData, 200, [
        'Content-Type' => 'application/json'
    ]);
}


   
    
   



    #[Route('/addEvent/{nomEvent}/{emplacement}/{descriptionEvent}/{prix}/{affiche}/{nbrMax}/', name: 'addE', methods: ['GET'])]
    public function addEvent($nomEvent,$emplacement,$descriptionEvent,$prix,$affiche,$nbrMax)
        {
     //$user = $this->getDoctrine()->getRepository(User::class)->find(12);
        $event = new Evenement();
        $event->setNomEvent($nomEvent);
        $event->setEmplacement($emplacement);
        $event->setDescriptionEvent($descriptionEvent);
        $event->setPrix($prix);
        $event->setAffiche($affiche);
        $event->setNbrMax($nbrMax);
       
        
    
        
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($event);
        $entityManager->flush();
        
        return new Response('Evenement added' );
    }

    #[Route('/supprimerE/{id}', name: 'suppApi', methods: ['GET'])]
    public function supprimerE($id, Request $request,  EntityManagerInterface $entityManager): JsonResponse
    {

        $event = $entityManager->getRepository(Evenement::class)->find($id);
        $em = $this->getDoctrine()->getManager();
        $em->remove($event);
        $em->flush();       
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formated = $serializer->normalize("Evenement supprimé avec succées ");
        return new JsonResponse($formated);
       
    }

    #[Route('/updateEvent/{idEvent}/{nomEvent}/{emplacement}/{descriptionEvent}/{prix}/{affiche}/{nbrMax}', name: 'updateE', methods: ['GET'])]
    public function updateEvent($idEvent,$nomEvent,$emplacement,$descriptionEvent,$prix,$affiche,$nbrMax, EntityManagerInterface $entityManager)
        {

            $event = $entityManager
            ->getRepository(Evenement::class)
            ->find($idEvent);
        $event->setNomEvent($nomEvent);
        $event->setEmplacement($emplacement);
        $event->setDescriptionEvent($descriptionEvent);
        $event->setPrix($prix);
        $event->setAffiche($affiche);
        $event->setNbrMax($nbrMax);

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($event);
        $entityManager->flush();
        
        return new Response('Evenement modified with ID: ' . $event->getIdEvent());
    }

}