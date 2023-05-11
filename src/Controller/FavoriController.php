<?php

namespace App\Controller;
use App\Entity\User;
use App\Entity\Produit;
use App\Entity\Favori;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use App\Repository\FavoriRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use App\Controller\EntityManagerInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;



class FavoriController extends AbstractController
{
   /* 
    #[Route('/favori', name: 'app_favori')]
    public function index(): Response
    {
        return $this->render('favori/index.html.twig', [
            'controller_name' => 'FavoriController',
        ]);
    }




    #[Route('/favori/add/{produitId}', name: 'app_favori_add')]
    public function add(Request $request, Produit $produit): Response
    
    {
        $user = $this->getUser();
    $favori = new Favori();
    $favori->setUser($user);
    $favori->setProduit($produit);
    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($favori);
    $entityManager->flush();
    $this->addFlash('success', 'Produit ajouté aux favoris !');

    return $this->redirectToRoute('app_produit_index');
    }
    

    #[Route('/favori/list', name: 'app_favori_list')]
public function list(Request $request): Response
{
    $user = $this->getUser();
    $favoris = $this->getDoctrine()->FavoriRepository->findBy(['user' => $user]);

    return $this->render('favori/list.html.twig', [
        'favoris' => $favoris,
    ]);
}

*/





#[Route('/favoris/add/{id_produit}', name: 'favoris_add')]
public function add($id_produit): Response
{
    // Get the current user
    $user = $this->getDoctrine()->getRepository(User::class)->find(3);

    // Get the product by ID
    $produit = $this->getDoctrine()->getRepository(Produit::class)->find($id_produit);

    // Check if the product exists
    if (!$produit) {
        throw $this->createNotFoundException('Produit non trouvé');
    }

    // Check if the user already has the product in favorites
    if ($produit->isFavoriByUser($user)) {
        $this->addFlash('warning', 'Le produit est déjà dans vos favoris');
        return $this->redirectToRoute('produits_index');
    }

    // Create a new Favori entity and set the user and product
    $favori = new Favori();
    $favori->setUser($user);
    $favori->setProduit($produit);

    // Save the favori to the database
    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($favori);
    $entityManager->flush();

    // Create a JSON response with the success status and ID of the favori entity
    $response = new JsonResponse([
        'success' => true,
        'favori_id' => $favori->getId(),
    ]);

    // Add a success message and redirect to the products index page
    $this->addFlash('success', 'Le produit a été ajouté à vos favoris');
    return $this->redirectToRoute('app_produit_index');
}

#[Route('/favoris/remove/{id_favori}', name: 'favoris_remove')]
public function remove($id_favori): Response
{
    // Get the current user
    $user = $this->getDoctrine()->getRepository(User::class)->find(1);

    // Get the favori by ID
    $favori = $this->getDoctrine()->getRepository(Favori::class)->find($id_favori);

    // Check if the favori exists and belongs to the user
    if (!$favori || $favori->getUser() != $user) {
        throw $this->createNotFoundException('Favori non trouvé');
    }

    // Get the ID of the favori entity before removing it
    $favori_id = $favori->getId();

    // Remove the favori from the database
    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->remove($favori);
    $entityManager->flush();

    // Create a JSON response with the success status and ID of the removed favori entity
    $response = new JsonResponse([
        'success' => true,
        'favori_id' => $favori_id,
    ]);

    // Add a success message and redirect to the products index page
    $this->addFlash('success', 'Le produit a été supprimé de vos favoris');
    return $this->redirectToRoute('app_produit_index');
}



#[Route('/favoris/toggle/{id}', name: 'favoris_toggle', methods: ['POST'])]
public function toggle(Request $request, $id): Response
{
    // Get the current user
    $user = $this->getDoctrine()->getRepository(User::class)->find(1);

   // Get the product by ID
   $produit = $this->getDoctrine()->getRepository(Produit::class)->find($id);

   // Check if the product exists
   if (!$produit) {
       throw $this->createNotFoundException('Produit non trouvé');
   }

   // Get the favori entity for the user and product
   $favori = $this->getDoctrine()->getRepository(Favori::class)->findOneBy([
       'user' => $user,
       'produit' => $produit,
   ]);

   // Toggle the favori status
   if ($favori) {
       // If the favori already exists, remove it
       $entityManager = $this->getDoctrine()->getManager();
       $entityManager->remove($favori);
       $entityManager->flush();
       $favori_id = null;
   } else {
       // If the favori does not exist, create it
       $favori = new Favori();
       $favori->setUser($user);
       $favori->setProduit($produit);
       $entityManager = $this->getDoctrine()->getManager();
       $entityManager->persist($favori);
       $entityManager->flush();
       $favori_id = $favori->getId();
   }

   // Create a JSON response with the success status and ID of the favori entity
   $response = new JsonResponse([
       'success' => true,
       'favori_id' => $favori_id,
   ]);

   return $response;
}









/*

#[Route('/add_favori/{id}', name: 'app_favori_add')]
public function addFavori(Request $request, Produit $produit): Response
{
    // Retrieve the currently authenticated user
    $usser = $this->getUser();
    if (!$usser) {
        throw $this->createAccessDeniedException();
    }

    // Create a new Favori object and set its properties
    $favori = new Favori();
    $favori->setProduit($produit);
    $user = $this->getDoctrine()->getRepository(User::class)->find(2);
    $favori->setUser($user);

    // Persist the Favori object to the database
    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($favori);
    $entityManager->flush();

    // Redirect the user back to the product index page
    return $this->redirectToRoute('app_produit_index');
}

*/

}
