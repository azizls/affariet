<?php

namespace App\Controller;

use App\Form\EchangeTypeSearch;
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
use App\Entity\Echange;
use App\Repository\ProduitRepository;
use App\Repository\EchangeRepository;
use App\Entity\Produit;
use Swift_Mailer;
use Swift_Message;
use Swift_SmtpTransport;
use Symfony\Component\Mercure\PublisherInterface;
use Symfony\Component\Mercure\Update;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Security\Core\Security;

class EchangeController extends AbstractController
{
    
    private $entityManager;
    #[Route('/echange', name: 'app_echange')]
    public function index(): Response
    {
        return $this->render('front.html.twig', [
            'controller_name' => 'EchangeController',
        ]);
    }
    #[Route('/echange/afficherEchange/{id}', name: 'app_readProduitPriver_echange')]
    public function readProduitPriver($id,ProduitRepository $repPro,Security $security): Response
    {
        $produit = $repPro->find($id);
        
        $r = $this->getDoctrine()->getRepository(ProduitPriver::class);
        //Utiliser findAll()
        $user = $security->getUser();
        $produits = $r->findProduitPrivesByUserAndDispo($user);

        dump($produits); // <-- add this line to debug
        $erreur;
        return $this->render('echange/afficherEchange.html.twig', [
            'p' => $produits,'id'=>$id,
        ]);
   
    }
    #[Route('/echange/afficherEchangeNotif', name: 'app_readProduitPriver_echange_notif')]
    public function readEchangeNotif(EchangeRepository $rep,ProduitRepository $repPro,Security $security): Response
    {
        $produits=$repPro->findAll();
        $user = $security->getUser();
        $echanges=$rep->ListeEchangeByUser($user);
        $filteredProduits = array();
        $produitIds = array();

        foreach ($produits as $produit) {
            if ($produit->getIdUser() === $user) {
                foreach ($echanges as $echange) {
                    if ($produit->getIdProduit() === $echange->getProduit()->getIdProduit()) {
                        if (!in_array($produit->getIdProduit(), $produitIds)) {
                            $filteredProduits[] = $produit;
                            $produitIds[] = $produit->getIdProduit();
                        }
                    }
                }
            }
        }
        

       
        return $this->render('echange/afficherEchangeNotif.html.twig', [
            'e' => $echanges,'p'=>$filteredProduits,
        ]);
    }







   



    public function sendEmail(MailerInterface $mailer): Response
    {
        $email = (new Email())
            ->from('jawherjaziri2023@gmail.com')
            ->to('jawherjaziri2023@gmail.com')
            //->cc('cc@example.com')
            //->bcc('bcc@example.com')
            //->replyTo('fabien@example.com')
            //->priority(Email::PRIORITY_HIGH)
            ->subject('Time for Symfony Mailer!')
            ->text('Sending emails is fun again!')
            ->html('<p>See Twig integration for better HTML integration!</p>');

        $mailer->send($email);

        // ...
    }












    #[Route('/echange/afficherEchangeUpdate/{id}', name: 'app_readProduitPriver_echange_update')]
    public function readProduitPriverupdate($id,Security $security): Response
    {
        
        $r = $this->getDoctrine()->getRepository(ProduitPriver::class);
        //Utiliser findAll()
        $user = $security->getUser();
        $produits = $r->findProduitPrivesByUserAndDispo($user);

        dump($produits); // <-- add this line to debug

        return $this->render('echange/afficherEchangeUpdate.html.twig', [
            'p' => $produits,'id'=>$id,
        ]);
    }
    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }



    

    

    
    #[Route('/echange/create/{id}/{id_PP}', name: 'echange_create')]
    public function create($id,$id_PP,  ProduitRepository $repPro,ProduitPriverRepository $rep, 
    ManagerRegistry $doctrine,MailerInterface $mailer,Security $security): Response
    {
        // Get the IDs of the two products being exchanged from the form data
        $produit = $repPro->find($id);
        $produitPriver=$rep->find($id_PP);
        if($produitPriver->getIduser()->getIdUser()!=$produit->getIdUser()->getIdUser()){
        if($produitPriver->getEtat()=='disponible'){
        $produitPriver->setEtat('utiliser');
        // Create a new Echange entity and set its properties
        $echange = new Echange();
        $echange->setProduit($produit);
        $echange->setProduitPrives($produitPriver);

        // Set the user of the Echange entity
        $user = $security->getUser();
        $echange->setUserP($user);

        // Save the Echange entity to the database
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($echange);
        $entityManager->flush();
        $dsn = getenv('MAILER_DSN');
        $transport = new Swift_SmtpTransport('smtp.gmail.com', 587, 'tls');
        $transport->setUsername('jawherjaziri2023@gmail.com');
        $transport->setPassword('whmlmiirylrkifva');

// Create a new mailer instance with the transport object
$mailer = new Swift_Mailer($transport);


    //BUNDLE MAILER
    $imagePath = $this->getParameter('produit_priver_images_directory').'/'.$produitPriver->getImage();

// Define the HTML content of the message
$htmlContent = '<html><body>';
$htmlContent .= '<p>Vous pouvez consulter le site. Une proposition d\'échange a été voicie quelque detail de produit produit proposer </p>';
$htmlContent .= '<p> Nom de Produit:<strong>' .$produitPriver->getNom().'</strong>.</p>';
$htmlContent .= '<p>Description: '.$produitPriver->getDescription().'</p>';
$htmlContent .= '<img src="'.$imagePath.'" alt="Image du produit" style="width:200px;height:200px;">';
$htmlContent .= '</body></html>';

// Set the HTML content as the body of the message
$message = (new Swift_Message('Proposition d\'échange'))
    ->setFrom('jawherjaziri2023@gmail.com')
    ->setTo($produit->getIdUser()->getEmail())
    ->setBody($htmlContent, 'text/html');

// Attach the image file to the message
$imageFile = \Swift_Attachment::fromPath($imagePath);
$message->attach($imageFile);
        
    //send mail
    $mailer->send($message);
        // Redirect the user to a success page
        return $this->redirectToRoute('app_readechangeDMoi');
        }
    }
        else {
            $r = $this->getDoctrine()->getRepository(ProduitPriver::class);
            $produits = $r->findAll();
            $this->addFlash('error', 'C votre produit');
            return $this->redirectToRoute('app_readProduitPriver_echange', [
                'p' => $produits,
                'id' => $id
            ]);
        }
        return $this->redirectToRoute('app_readechangeDMoi');
    }

    #[Route('/echange/update/{id}/{id_PP}', name: 'echange_update')]
    public function updateEchange($id, $id_PP, EchangeRepository $repE,ProduitPriverRepository $rep, ManagerRegistry $doctrine,Security $security): Response
{
    // Get the Echange entity that you want to update from the database
    $entityManager = $doctrine->getManager();
    $echange = $repE->find($id);
    // Get the new ProduitPriver entity that you want to associate with the Echange entity
    $produitPriver = $rep->find($id_PP);
    if($produitPriver->getEtat()=='disponible'){
    if($produitPriver->getId() != $echange->getProduitPrives()->getId()){
        $oldproduitpriver = $echange->getProduitPrives();
        if ($oldproduitpriver  !== null) {
            $oldproduitpriver ->setEtat('disponible');
        }
        if ($oldproduitpriver !== null) {
            $entityManager->persist($oldproduitpriver);
        }
    // Set the ProduitPrives property of the Echange entity to the new ProduitPriver entity
    $produitPriver->setEtat('utiliser');
    $echange->setProduitPrives($produitPriver);

    // Save the changes to the database
    $entityManager->flush();

    // Redirect the user to a success page
    return $this->redirectToRoute('app_readechangeDMoi');
    }
    
}
if ($produitPriver->getEtat() != 'disponible') {
    $r = $this->getDoctrine()->getRepository(ProduitPriver::class);
    $produits = $r->findAll();
    $this->addFlash('error', 'Le produit privé est déjà utilisé.');
    return $this->redirectToRoute('app_readProduitPriver_echange_update', [
        'p' => $produits,
        'id' => $id
    ]);
}
}



#[Route('/echange/afficheEchangeback', name: 'app_readechangeback')]
public function readEchangeBack(Request $request)
{    
    $e = $this->getDoctrine()->getRepository(Echange::class);
    //Utiliser findAll()
    //$produitPrives = $r->findAll();
    //$produits=$p->findAll();
    $echanges=$e->findAll();
    

    $results = null;
    
   
    //dump($produitPrives); // <-- add this line to debug
    //dump($produits);
    
    return $this->render('echange/afficheEchangeback.html.twig', [
        'e'=> $echanges,
    ]);
}









    #[Route('/echange/EchangeProposerDMoi', name: 'app_readechangeDMoi')]
    public function readEchangeProposerDmoi(Security $security): Response
    {
        $user = $security->getUser();
        //$r = $this->getDoctrine()->getRepository(ProduitPriver::class);
        //$p = $this->getDoctrine()->getRepository(Produit::class);
        $e = $this->getDoctrine()->getRepository(Echange::class);
        //Utiliser findAll()
        //$produitPrives = $r->findAll();
        //$produits=$p->findAll();
        $echanges=$e->findAll();
        $filteredEchanges = array();
        foreach ($echanges as $echange) {
            if ($echange->getUserP() === $user) {
        $filteredEchanges[] = $echange;
            }
            }
        //dump($produitPrives); // <-- add this line to debug
        //dump($produits);
        dump($echanges);
        return $this->render('echange/EchangeProposerDMoi.html.twig', [
            /*'pp' => $produitPrives,'p' => $produits,*/'e'=> $filteredEchanges,
        ]);
    }
    #[Route('/echange/EchangeDelete/{id}', name: 'app_EchangeDelete')]
    public function delecteEchange($id, EchangeRepository $rep, ProduitRepository $repPro, ManagerRegistry $doctrine): Response
    {
        //récupérer la classe à supprimer
        $echange = $rep->find($id);
        $produitpriver = null;
        
        if ($echange !== null && $echange->getProduitPrives() !== null) {
            $produitpriver = $echange->getProduitPrives();
            //$repPro->find($echange->getProduitPrives()->getId());
        }
        
        if ($produitpriver !== null) {
            $produitpriver->setEtat('disponible');
        }
        
        //Action de suppression
        //récupérer l'Entitye manager
        $em = $doctrine->getManager();
        $em->remove($echange);
        
        if ($produitpriver !== null) {
            $em->persist($produitpriver);
        }
        
        //La maj au niveau de la bd
        $em->flush();
        
        return $this->redirectToRoute('app_readechangeDMoi');
    }
    #[Route('/echange/EchangeDeleteReceiverView/{id}', name: 'app_EchangeDeleteReceiverView')]
    public function delecteEchangeReceiverView($id, EchangeRepository $rep, ProduitRepository $repPro, ManagerRegistry $doctrine): Response
    {
        //récupérer la classe à supprimer
        $echange = $rep->find($id);
        $produitpriver = null;
        
        if ($echange !== null && $echange->getProduitPrives() !== null) {
            $produitpriver = $echange->getProduitPrives();
            //$repPro->find($echange->getProduitPrives()->getId());
        }
        
        if ($produitpriver !== null) {
            $produitpriver->setEtat('disponible');
        }
        
        //Action de suppression
        //récupérer l'Entitye manager
        $em = $doctrine->getManager();
        $em->remove($echange);
        
        if ($produitpriver !== null) {
            $em->persist($produitpriver);
        }
        
        //La maj au niveau de la bd
        $em->flush();
        
        return $this->redirectToRoute('app_readProduitPriver_echange_notif');
    }
    
}
