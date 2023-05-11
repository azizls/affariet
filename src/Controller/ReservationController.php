<?php

namespace App\Controller;

use App\Entity\Reservation;
use App\Form\ReservationType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Evenement;
use App\Entity\User;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use App\Repository\ReservationRepository;
use Doctrine\Persistence\ManagerRegistry;
use App\Repository\EvenementRepository;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Swift_Mailer;
use Swift_Message;
use Swift_SmtpTransport;
use Endroid\QrCode\QrCode;
use Symfony\Component\Mime\Part\DataPart;
use Endroid\QrCode\Color\Color;
use Endroid\QrCode\Encoding\Encoding;
use Endroid\QrCode\ErrorCorrectionLevel\ErrorCorrectionLevelLow;
use Endroid\QrCode\Label\Label;
use Endroid\QrCode\Logo\Logo;
use Endroid\QrCode\RoundBlockSizeMode\RoundBlockSizeModeMargin;
use Endroid\QrCode\Writer\PngWriter;
use Endroid\QrCode\Writer\ValidationException;
use Endroid\QrCode\Label\Margin\Margin;
use Endroid\QrCode\Builder\BuilderInterface;
use Endroid\QrCode\ErrorCorrectionLevel\ErrorCorrectionLevelHigh;
use Endroid\QrCode\Label\Alignment\LabelAlignmentCenter;
use Swift_Attachment;
use Symfony\Component\Security\Core\Security;



#[Route('/reservation')]
class ReservationController extends AbstractController
{
    private $builder;

public function __construct(BuilderInterface $builder)
{
    $this->builder = $builder;
}

    #[Route('/back', name: 'app_reservation_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
    
    $entityManager = $this->getDoctrine()->getManager();

    $reservations = $entityManager->createQuery(
        'SELECT r, e
        FROM App\Entity\Reservation r
        JOIN r.idEvent e'
    )->getResult();
        return $this->render('reservation/index.html.twig', [
            'reservations' => $reservations,
        ]);
    }
    #[Route('/', name: 'app_reservation_front_index', methods: ['GET'])]
    public function front_index(EntityManagerInterface $entityManager,Security $security): Response
    {
    
    $entityManager = $this->getDoctrine()->getManager();
    $userId = $security->getUser();
    
    $reservations = $entityManager->createQuery(
        'SELECT r, e
        FROM App\Entity\Reservation r
        JOIN r.idEvent e
        WHERE r.idUser1 = :userId'
    )->setParameter('userId', $userId)
    ->getResult();
        return $this->render('reservation/front_index.html.twig', [
            'reservations' => $reservations,
        ]);
    }

    #[Route('/new', name: 'app_reservation_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $reservation = new Reservation();
        $form = $this->createForm(ReservationType::class, $reservation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($reservation);
            $entityManager->flush();

            return $this->redirectToRoute('app_reservation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reservation/new.html.twig', [
            'reservation' => $reservation,
            'form' => $form,
        ]);
    }

    #[Route('/bakc/{idRes}', name: 'app_reservation_show', methods: ['GET'])]
    public function show(Reservation $reservation): Response
    {
        $evenement = $reservation->getIdEvent();

        return $this->render('reservation/show.html.twig', [
        'reservation' => $reservation,
        'evenement' => $evenement,
        ]);
    }
    #[Route('/{idRes}', name: 'app_reservation_front_show', methods: ['GET'])]
    public function front_show(Reservation $reservation,EntityManagerInterface $em): Response
    {         
        $evenement = $reservation->getIdEvent();

        return $this->render('reservation/front_show.html.twig', [
        'reservation' => $reservation,
        'evenement' => $evenement,
        ]);
    }

    #[Route('/{idRes}/edit', name: 'app_reservation_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Reservation $reservation, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(ReservationType::class, $reservation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_reservation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reservation/edit.html.twig', [
            'reservation' => $reservation,
            'form' => $form,
        ]);
    }

    #[Route('/{idRes}', name: 'app_reservation_delete', methods: ['POST'])]
    public function delete(Request $request, Reservation $reservation, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reservation->getIdRes(), $request->request->get('_token'))) {
            $entityManager->remove($reservation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_reservation_front_index', [], Response::HTTP_SEE_OTHER);
    }

/**
 * @Route("/reserver/{id}", name="reservation_create")
 */
public function create(Request $request, $id,EntityManagerInterface $em,ReservationRepository $r,EvenementRepository $e,Swift_mailer $mailer,Security $security )
{
   

    $event = $em->getRepository(Evenement::class)->find($id);
    //$user = $this->getUser();
    $user = $security->getUser();
    //$id_user = $user->getIdUser();

    // Vérifie si l'utilisateur a déjà une réservation pour cet événement
    $hasReserved = $this->hasUserReservedEvent($id, $user);
        $compteur = $event-> getNbrMax();
    
    // Créer un formulaire pour la saisie du nombre de réservations
    $form = $this->createFormBuilder()
        ->add('nombre_reservations', IntegerType::class, [
            'label' => 'Nombre de réservations souhaité :',
            'attr' => ['min' => 1, 'max' => $compteur]
        ])
        ->add('submit', SubmitType::class, [
            'label' => 'Réserver',
            'attr' => ['class' => 'btn btn-primary']
        ])
        ->getForm();
    if (!$hasReserved && $compteur > 0){

        // Traiter la soumission du formulaire
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
        // Récupérer le nombre de réservations souhaité
        $data = $form->getData();
        $nombreReservations = $data['nombre_reservations'];
        $NbrReservationFinal = $compteur -  $nombreReservations;
        $event -> setNbrMax($NbrReservationFinal);
        $reservation = new Reservation();
        $reservation->setIdEvent($event);
        $reservation->setIdUser1($user);
        $reservation->setDateRes(new \DateTime());
        $reservation->setNbrDeRes($nombreReservations);
        $em->persist($reservation);
        $em->flush();
        $sendmailer = $this->email($user,$mailer,$reservation);
        $this->addFlash('success', 'Réservation effectuée avec succès !');
        
        return $this->redirectToRoute('app_reservation_front_index', ['id' => $id]);
        }
    }
    else {
        // Si une réservation existe déjà, redirige vers la page des réservations avec un message d'erreur
        $this->addFlash('error', 'Vous avez déjà réservé cet événement.');
        return $this->redirectToRoute('app_reservation_front_index', ['id' => $id]);

        }
    
    // Afficher le formulaire
    return $this->render('reservation/form.html.twig', [
        'form' => $form->createView(),
        'event' => $event,
        'ccc' => $compteur,
        'X' => $hasReserved,
    ]);

}
public function hasUserReservedEvent($id, $userId)
    {
        $entityManager = $this->getDoctrine()->getManager();
        $reservationRepository = $entityManager->getRepository(Reservation::class);

        $qb = $reservationRepository->createQueryBuilder('r')
            ->select('COUNT(r.idRes)')
            ->where('r.idEvent = :eventId')
            ->andWhere('r.idUser1 = :userId')
            ->setParameter('eventId', $id)
            ->setParameter('userId', $userId);

        $count = $qb->getQuery()->getSingleScalarResult();
        if ($count > 0){
            return true;
        }
        return  false;
    }


    public function email($user, $mailer, $reservation)
{
    // Create a new SMTP transport with the desired configuration
    $transport = new Swift_SmtpTransport('smtp.gmail.com', 587, 'tls');
    $transport->setUsername('zakaria.bouharb771@gmail.com');
    $transport->setPassword('vvbfelaynbyavtlf');

    // Create a new mailer instance with the transport object
    $mailer = new Swift_Mailer($transport);

    $qrcodeDataUri = $this->qrcode($reservation); // Generate QR code and get its data URI

    // Create a new Swift_Message object and set its properties
    $message = (new Swift_Message())
        ->setSubject('Confirmation de la reservation')
        ->setFrom('zakaria.bouharb771@gmail.com')
        ->setTo($user->getEmail())
        ->setBody("<p>Bonjour</p> Reservation effectuer avec succes, voici les information relatives a votre reservation :");

    // Create a new Swift_Attachment object from the data URI and attach it to the message
    $attachment = new Swift_Attachment(base64_decode(substr($qrcodeDataUri, strpos($qrcodeDataUri, ',') + 1)), 'qr-code.png', 'image/png');
    $message->attach($attachment);

    // Send email
    $mailer->send($message);
}

    
    
/**
* @Route("/annuler-reservation/{id}", name="app_reservation_front_delete")
*/
public function delete_front(Request $request, EntityManagerInterface $entityManager, $id)
{
   $reservationRepository = $entityManager->getRepository(Reservation::class);
   $reservation = $reservationRepository->find($id);
   $event = $reservation->getIdEvent();
   $compteur = $event->getNbrMax();
   $nombreReservations = $reservation->getNbrDeRes();
   
   // Vérifier si le formulaire a été soumis
   $form = $this->createFormBuilder()
       ->add('nombre_reservations', IntegerType::class, [
           'label' => 'Nombre de réservations à annuler :',
           'attr' => ['min' => 1, 'max' => $nombreReservations]
       ])
       ->add('submit', SubmitType::class, [
           'label' => 'Annuler',
           'attr' => ['class' => 'btn btn-danger']
       ])
       ->getForm();

   $form->handleRequest($request);

   if ($form->isSubmitted() && $form->isValid()) {
       $data = $form->getData();
       $nombreReservationsASupprimer = $data['nombre_reservations'];
       if ($nombreReservationsASupprimer < $nombreReservations) {
           // Calculer le nouveau nombre de réservations pour l'événement
           $event->setNbrMax($compteur + $nombreReservationsASupprimer);
           $nbrResActualisé = $nombreReservations -$nombreReservationsASupprimer ;
           $nombreReservations = $reservation->setNbrDeRes($nbrResActualisé);
           $reservation->setIdEvent($event);
           $entityManager->persist($reservation);
           $entityManager->flush();
           $this->addFlash('success', 'La réservation a été mise a jour avec succès !');
       }  
       elseif ($nombreReservationsASupprimer === $nombreReservations){
           // Supprimer la réservation
           $entityManager->remove($reservation);
           $entityManager->flush();
           $this->addFlash('success', 'La réservation a été annulée avec succès !');
       } else {
           // Afficher un message d'erreur si le nombre de réservations à supprimer est supérieur au nombre de réservations effectuées
           $this->addFlash('error', 'Le nombre de réservations à annuler ne peut pas être supérieur au nombre de réservations effectuées !');
       }

       return $this->redirectToRoute('app_reservation_front_index');
   }

   return $this->render('reservation/front_delete.html.twig', [
       'form' => $form->createView(),
       'reservation' => $reservation
   ]);
}

#public function generateQrCode($reservation)
#{
    // Créez une instance de la classe QrCode avec les paramètres souhaités
//    $qrContent = "Evenement: ".$reservation->getIdEvent()->getNomEvent().", Emplacement: ".$reservation->getIdEvent()->getEmplacement().", Prix: ".$reservation->getIdEvent()->getPrix()."DT";
 //   $qrCode = new QrCode($qrContent);

    // Récupérez le générateur d'image et configurez-le pour générer une image PNG
   // $qrCodeGenerator = $this->get('endroid.qrcode.generator');
   // $qrCodeGenerator->setFormat('png');

    // Générez le QR code en utilisant le générateur d'image
   // $qrCodeImage = $qrCodeGenerator->generate($qrCode);

    // Retournez une réponse HTTP avec le contenu de l'image générée
    //return new Response($qrCodeImage, 200, ['Content-Type' => 'image/png']);
#}



    // public function getEventInfoString(Reservation $reservation)
    //  {
    
    //     $qrCodeContent = '';

    //     $event = $reservation->getIdEvent();
    // if ($event) {

    //     $qrCodeContent .= 'BEGIN:VEVENT' . PHP_EOL;
    //     $qrCodeContent .= 'SUMMARY:' . $reservation->getIdEvent()->getTitle() . PHP_EOL;
    //     $qrCodeContent .= 'DTSTART:' . date_format($reservation->getIdEvent()->getDateBeg(), 'Ymd\THis\Z') . PHP_EOL;
    //     $qrCodeContent .= 'DTEND:' . date_format($reservation->getIdEvent()->getDateEnd(), 'Ymd\THis\Z') . PHP_EOL;
    //     $qrCodeContent .= 'Nombre de places réservées: ' . $reservation->getNbPlace() . "\n";
    //     $qrCodeContent .= 'DESCRIPTION:' . $reservation->getIdEvent()->getDescription() . PHP_EOL;
    //     $qrCodeContent .= 'END:VEVENT';
    // }
      

    //     return $qrCodeContent;
    //  }

    public function qrcode($reservation)
    {
        // $url = 'https://www.google.com/search?q=';
    
        $objDateTime = new \DateTime('NOW');
        $dateString = $objDateTime->format('d-m-Y H:i:s');
    
        $path = dirname(__DIR__, 2).'/public/uploads/images/evenement';
        $qrContent = "Evenement: ".$reservation->getIdEvent()->getNomEvent().", Emplacement: ".$reservation->getIdEvent()->getEmplacement().", Event description:".$reservation->getIdEvent()->getDescriptionEvent().", Prix: ".$reservation->getIdEvent()->getPrix()."DT";
    
        // set qrcode
        $result = $this->builder
            ->data($qrContent)
            ->encoding(new Encoding('UTF-8'))
            ->errorCorrectionLevel(new ErrorCorrectionLevelHigh())
            ->size(400)
            ->margin(10)
            ->labelText($dateString)
            ->labelAlignment(new LabelAlignmentCenter())
            ->labelMargin(new Margin(15, 5, 5, 5))
//           ->logoPath($path.'/img/logo.png')
//            ->logoResizeToWidth('100')
//            ->logoResizeToHeight('100')
            ->backgroundColor(new Color(221, 158, 3))
            ->build()
        ;
            
        //generate name
        $namePng = uniqid('', '') . '.png';
    
        //Save img png
        $result->saveToFile($path.'/qr-code/'.$namePng);
    
        return $result->getDataUri();
    }

    
    }


