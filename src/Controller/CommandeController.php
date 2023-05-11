<?php

namespace App\Controller;
use App\Entity\ProduitPriver;
use App\Form\ProduitPriverType;
use App\Form\PayementType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\User;
use App\Repository\UserRepository;
use Doctrine\Persistence\ManagerRegistry;
use App\Repository\ProduitRepository;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\Commande;
use App\Repository\CommandeRepository;
use DateTime;
use Stripe\Stripe;
use Stripe\Customer;
use Stripe\Charge;
use Stripe\Token;
use Twilio\Rest\Client;
use Stripe\Refund;
use App\Entity\Produit;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Security\Core\Security;
class CommandeController extends AbstractController
{
    #[Route('/commande', name: 'app_commande')]
    public function index(): Response
    {
        return $this->render('commande/index.html.twig', [
            'controller_name' => 'CommandeController',
        ]);
    }
    #[Route('/commande/show', name: 'app_readCommande')]
public function readCommandes( Security $security): Response
{
    $r = $this->getDoctrine()->getRepository(Commande::class);

    $user = $security->getUser();
    $commandes = $r->findCommandesByUser($user);

    $numbProduit = array();
    foreach ($commandes as $commande) {
        $produit = $commande->getProduit();
        $quantite=0;
        $quantite = $r->countProduitByUser($produit, $user);
        if (array_key_exists($produit->getIdProduit(), $numbProduit)) {
           
            
        } else {
            $numbProduit[$produit->getIdProduit()] = array(
                'produit' => $produit,
                'quantite' => $quantite,
                'total' => $quantite * $produit->getPrixProduit()
            );
        }
    }
   

    return $this->render('commande/show.html.twig', [
        'numbProduit' => $numbProduit,
        'commandes' => $commandes,
    ]);
}
#[Route('/commande/stat', name: 'app_readCommandestat')]
public function Commandesstat(CommandeRepository $rep): Response
{
    $commandes = $rep->findAll();
    $stats = [];

    // Initialize counters for each month
    for ($month = 1; $month <= 12; $month++) {
        $stats[$month] = 0;
    }

    // Loop over each command to count the number of commands for each month
    foreach ($commandes as $commande) {
        $month = (int)$commande->getDateCommande()->format('n');
        $stats[$month]++;
    }

    // Pass the stats array to the template to display the results
    return $this->render('commande/stat.html.twig', [
        'stats' => $stats,
    ]);
}


#[Route('/deleteCommande/{id}', name: 'app_deleteCommande')]
    public function delecteC($id, CommandeRepository $rep, 
    ManagerRegistry $doctrine): Response
    {
        Stripe::setApiKey('sk_test_51MekuZBuy83JycrRFwccFuthQTjhGfDUOGsgImRUeUxYnbUNLkNivEA10Wm6YI1b4LfmmFc37TCZs6jkv4ERDckf00mCAL6jCe');
        //récupérer la classe à supprimer
        $commande=$rep->find($id);
        $chargeId = $commande->getChargeId();
        
        $refund = Refund::create([
            'charge' => $chargeId
        ]);
        //Action de suppression
        //récupérer l'Entitye manager
        $em=$doctrine->getManager();
        $em->remove($commande);
        //La maj au niveau de la bd
        $em->flush();
        return $this->redirectToRoute('app_readCommande');
    }
    
    #[Route('/newCommande/{id}', name: 'payement')]
    public function processPayment( $id, ProduitRepository $repPro, 
    ManagerRegistry $doctrine,Request $request, Security $security): Response
{
    // Set your Stripe API key
    Stripe::setApiKey('sk_test_51MekuZBuy83JycrRFwccFuthQTjhGfDUOGsgImRUeUxYnbUNLkNivEA10Wm6YI1b4LfmmFc37TCZs6jkv4ERDckf00mCAL6jCe');
    
    

    $user = $security->getUser();
    $form = $this->createForm(PayementType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $produit = $repPro->find($id);
        
       
           
    $data = $form->getData();
         
    $cardNumber = $data['card_number'];
    $expMonth = $data['exp_month'];
    $expYear = $data['exp_year'];
    $cvc = $data['cvc'];

    // Create a new card in Stripe
    $card = [
        'number' => $cardNumber,
        'exp_month' => $expMonth,
        'exp_year' => $expYear,
        'cvc' => $cvc,
    ];

    // Create a new token in Stripe
    $token = Token::create([
        'card' => $card,
    ]);

    // Use the token to charge the customer's card
    $customer = Customer::create([
        'source' => $token->id,
        'email' => $user->getEMail(), // The customer's email address
    ]);

    // Charge the customer's card
    $charge = Charge::create([
        'amount' => $produit->getPrixProduit(), // the amount to charge in cents
        'currency' => 'usd', // the currency of the charge
        'customer' => $customer->id, // the ID of the customer to charge
    ]);
    $commande=new Commande();
    $commande->setProduit($produit);
   
    $dateCommande = new DateTime();
    $commande->setDateCommande($dateCommande);
    $commande->setChargeId($charge->id);
    // Set the user of the Echange entity
    
    $commande->setUser($user);

    // Save the Echange entity to the database
    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($commande);
    $entityManager->flush();
   


    $accountSid = 'AC5ae2ad78f03174b6b1168cae9575ded6';
$authToken = '1c160d54ee6229a8cdc59ed68aac92a6';
       if (!$accountSid || !$authToken) {
           throw new \Exception('Missing Twilio credentials. Please check your .env file.');
        }
        $twilio = new Client($accountSid, $authToken);

        $message = $twilio->messages->create(
            '+216 24 145 922',
            [
                'from' => '+16204079413',
                'body' => "votre paiement est effectuer avec succées avec un montant de TN{$produit->getPrixProduit()}"
            ]
        );
        $this->addFlash('success', 'votre paiement est effectuer avec succées !');
        return $this->redirectToRoute('app_readCommande');
}
    // Redirect the user to a success page
    
    return $this->render('commande/newCommande.html.twig', [
        'form' => $form->createView(),
        
        'id' => $id
    ]);
}


#[Route('/commande/showjson/json', name: 'listCommande')]
public function readCommandesjson(NormalizerInterface $normalizer): Response
{
    $r = $this->getDoctrine()->getRepository(Commande::class);

    $user = $this->getDoctrine()->getRepository(User::class)->find(12);
    $commandes = $r->findAll();

    // Transform the entities into an array of data
    $commandesData = array_map(function ($commande) {
        return [
            'id' => $commande->getId(),
            'date_commande' => $commande->getDateCommande()->format('Y-m-d'),
            'id_produit' => $commande-> getProduit() ? $commande-> getProduit()-> getIdProduit() : null,
            'nom_produit' => $commande-> getProduit() ? $commande-> getProduit()->getNomProduit() : null,
            'prix_produit' => $commande->getProduit() ? $commande->getProduit()->getPrixProduit() : null,
            'user' => $commande->getUser()->getIdUser(),
            'chargeId' => $commande->getChargeId(),
        ];
    }, $commandes);

     $commandesNormalises = $normalizer->normalize( $commandesData, 'json', ['groups' => "commandes"]);

    $json = json_encode($commandesNormalises);

    return new Response($json);
}
#[Route('/deleteCommandejson/{id}', name: 'app_deleteCommandejson')]
    public function delecteCjson($id, CommandeRepository $rep, 
    ManagerRegistry $doctrine,NormalizerInterface $normalizer): Response
    {
       
        //récupérer la classe à supprimer
        $commande=$rep->find($id);
       
        
        
        //Action de suppression
        //récupérer l'Entitye manager
        $em=$doctrine->getManager();
        $em->remove($commande);
        //La maj au niveau de la bd
        $em->flush();
        $commandesNormalises = $normalizer->normalize( $commande, 'json', ['groups' => "commandes"]);

    $json = json_encode($commandesNormalises);

    return new Response("commande delete successfully".$json);
    }
    #[Route('/commande/addjson/json', name: 'addCommandejson')]
public function addCommandejson(Request $req,NormalizerInterface $normalizer)
{
    $r = $this->getDoctrine()->getRepository(Commande::class);

    $user = $this->getDoctrine()->getRepository(User::class)->find(12);
    $produit = $this->getDoctrine()->getRepository(Produit::class)->find($req->get('produit'));
    $commande=new Commande();
    $commande->setProduit($produit);
    $charge_id=$req->get('charge_id');
    $dateCommande = new DateTime();
    $commande->setDateCommande($dateCommande);
    $commande->setChargeId($charge_id);
    // Set the user of the Echange entity
    
    $commande->setUser($user);

    // Save the Echange entity to the database
    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($commande);
    $entityManager->flush();

     $commandesNormalises = $normalizer->normalize( $commande, 'json', ['groups' => "commandes"]);

    $json = json_encode($commandesNormalises);

    return new Response($json);


}
}