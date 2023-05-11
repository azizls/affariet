<?php

namespace App\Controller;

use App\Entity\Annonce;
use App\Entity\User;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Security\Core\Security;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Doctrine\ORM\EntityManagerInterface;


class ApiAnnonceController extends AbstractController
{
    #[Route('/api/annonces', name: 'app_api_annonces', methods: ['GET'])]
    public function getAnnonces(): JsonResponse
    {
        $annonces = $this->getDoctrine()->getRepository(Annonce::class)->findAll();
        $data = [];

        foreach ($annonces as $annonce) {
            $data[] = [
                'idAnnonce' => $annonce->getIdAnnonce(),
                'type' => $annonce->getType(),
                'description' => $annonce->getDescription(),
                 'idUser' => $annonce->getIdUser(),
                'dateAnnonce' => $annonce->getDateAnnonce(),
                'image' => $annonce->getImage(),
            ];
        }

        return new JsonResponse($data);
    }

    #[Route('/api/annonces/add', name: 'app_api_annonces_add', methods: ['GET', 'POST'])]
public function addAnnonce(Request $req): JsonResponse
{

    $user = $this->getDoctrine()->getRepository(User::class)->find(12);
   // $idUser = $user->getIdUser();

    $annonce = new Annonce();
    $annonce->setType($req->get('type'));
    $annonce->setDescription($req->get('description'));
    $annonce->setIdUser($user);

    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($annonce);
    $entityManager->flush();

    return new JsonResponse([
        'idAnnonce' => $annonce->getIdAnnonce(),
        'message' => 'Annonce ajoutée avec succès'
    ]);
}


   #[Route('/api/annonces/delete/{id}', name: 'app_api_annonces_delete', methods:['GET'])]
   public function deleteAnnonce(int $id,  EntityManagerInterface $entityManager): JsonResponse
   {


    $annonce = $entityManager->getRepository(Annonce::class)->find($id);
    $em = $this->getDoctrine()->getManager();
    $em->remove($annonce);
    $em->flush();       
    $serializer = new Serializer([new ObjectNormalizer()]);
    $formated = $serializer->normalize("Annonce supprimé avec succées ");
    return new JsonResponse($formated);
   }
   
   #[Route('/api/annonces/{id}', name: 'app_api_annonces_edit', methods: ['PUT'])]
   public function editAnnonce(Request $request, int $id): JsonResponse
   {
       $annonce = $this->getDoctrine()->getRepository(Annonce::class)->find($id);
   
       if (!$annonce) {
           return new JsonResponse(['error' => 'Annonce non trouvée'], Response::HTTP_NOT_FOUND);
       }
   
       $data = json_decode($request->getContent(), true);
   
       $annonce->setType($data['Type'] ?? $annonce->getType());
       $annonce->setDescription($data['description'] ?? $annonce->getDescription());
       $annonce->setIdUser($data['idUser'] ?? $annonce->getIdUser());
   
       $entityManager = $this->getDoctrine()->getManager();
       $entityManager->persist($annonce);
       $entityManager->flush();
   
       return new JsonResponse(['message' => 'Annonce modifiée avec succès']);
   }
   

}

