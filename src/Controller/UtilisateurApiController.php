<?php

namespace App\Controller;

use App\Entity\User;
use App\Entity\Role;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\JsonResponse;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

class UtilisateurApiController extends AbstractController
{

    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }
    
   
    #[Route("/addUserJSON/new", name: "addUserJSON")]
    public function addUserJSON(Request $req, NormalizerInterface $Normalizer,EntityManagerInterface $entityManager)
    {  $role = $this->entityManager->getRepository(Role::class)->find(2);
        $em = $this->getDoctrine()->getManager();
        $user = new User();
        $user->setNom($req->get('nom'));
        $user->setPrenom($req->get('prenom'));
        $user->setNumero($req->get('numero'));
        $user->setMdp($req->get('mdp'));
        $user->setIsEnabled(1);
        $user->setIdRole($role);
        $user->setImage($req->get('image'));
        $user->setEmail($req->get('email'));
          //$hashedPassword = password_hash($Pwd, PASSWORD_DEFAULT);
       // $user->setPwd($hashedPassword);
        
        // $nomuser = trim($req->get('nomuser'));
        // $Prenomuser = trim($req->get('Prenomuser'));
        // $email = trim($req->get('email'));
        // $numtel = trim($req->get('numtel'));
        // $Pwd = $req->get('Pwd');
        // $Photo = trim($req->get('Photo'));
    
        // if (empty($nomuser)) {
        //     return new Response('Le champ NomUser est requis', 400);
        // }
        // $user->setNomuser($nomuser);
    
        // if (empty($Prenomuser)) {
        //     return new Response('Le champ Prenomuser est requis', 400);
        // }
        // $user->setPrenomuser($Prenomuser);
    
        // if (empty($email)) {
        //     return new Response('Le champ email est requis', 400);
        // }
        // $user->setemail($email);
    
        // if (empty($numtel)) {
        //     return new Response('Le champ numtel est requis', 400);
        // }
        // $user->setnumtel($numtel);
    
        // if (empty($Pwd)) {
        //     return new Response('Le champ Pwd est requis', 400);
        // }
        // $hashedPassword = password_hash($Pwd, PASSWORD_DEFAULT);
        // $user->setPassword($hashedPassword);
    
        // if (!empty($Photo)) {
        //     $user->setPhoto($Photo);
        // }
    
        $em->persist($user);
        $em->flush();
        
        $jsonContent = $Normalizer->normalize($user, 'json', ['groups' => 'users']);
        return new Response(json_encode($jsonContent));
    }
     
    
   
   
   
    


    

    /**
 * @Route("user/signin/json", name="app_login_jz")
 */
public function siginAction(Request $request) 
{
    $Email = $request->query->get("email");
    $Password = $request->query->get("mdp");

    $em = $this->getDoctrine()->getManager();
    $user = $em->getRepository(User::class)->findOneBy(['email'=>$Email]);

    if($user) {
        if($Password == $user->getPassword()) {
            $serializer = new Serializer([new ObjectNormalizer()]);
            $formatted = $serializer->normalize($user);
            return new JsonResponse($formatted);
        }
        else {
            return new JsonResponse(['error' => 'password not found'], JsonResponse::HTTP_UNAUTHORIZED);
        }
    }
    else {
        return new JsonResponse(['error' => 'user not found'], JsonResponse::HTTP_NOT_FOUND);
    }
}

    /**
 * @Route("user/editUser/json", name="app_gestion_profile")
 * @Method("PUT")
 */
public function editUser(Request $request, UserPasswordEncoderInterface $PasswordEncoder)
{
    $data = json_decode($request->getContent(), true);

    $id = $request->get("id");
    $UserName = $request->get("nom");
    $prenom = $request->get("prenom");
    //$Numero = $data['Numero'];
    $Email = $request->get("email");
    //$Adresse = $data['Adresse'];
    $mdp = $request->get("mdp");

    $em = $this->getDoctrine()->getManager();
    $user = $em->getRepository(User::class)->find($id);

    if (!$user) {
        return new JsonResponse(['message' => 'User not found'], Response::HTTP_NOT_FOUND);
    }

    //$user->setCIN($CIN);
    $user->setNom($UserName);
    $user->setPrenom($prenom);
    //$user->setNumero($Numero);
    $user->setEmail($Email);
    $user->setMdp($mdp);
    //$user->setAdresse($Adresse);
    //$user->setPassword($PasswordEncoder->encodePassword($user, $Password));

    try {
        $em = $this->getDoctrine()->getManager();
        $em->persist($user);
        $em->flush();

        return new JsonResponse(['message' => 'User updated successfully'], Response::HTTP_OK);
    } catch (\Exception $ex) {
        return new JsonResponse(['message' => 'Failed to update user: ' . $ex->getMessage()], Response::HTTP_INTERNAL_SERVER_ERROR);
    }
}


/**
 * @Route("user/deleteUser/json", name="delete_User")
 * @Method("DELETE")
 */
public function deletePostAction(Request $request) {
    $id = $request->get("id");

    $em = $this->getDoctrine()->getManager();
    $user = $em->getRepository(User::class)->find($id);

    if ($user != null) {
        $em->remove($user);
        $em->flush();

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize("L'utilisateur a été supprimé avec succès.");
        return new JsonResponse($formatted);
    }

    $formatted = ['error' => 'ID utilisateur invalide.'];
    return new JsonResponse($formatted, Response::HTTP_BAD_REQUEST);
}

/**
     * @Route("/user/getPasswordByEmail", methods={"GET"})
     */
    public function getPasswordByEmail(Request $request)
    {
        $email = $request->query->get('email');

        $entityManager = $this->getDoctrine()->getManager();
        $userRepository = $entityManager->getRepository(User::class);

        $user = $userRepository->findOneBy(['email' => $email]);

        if (!$user) {
            return new JsonResponse(['error' => 'User not found']);
        }

        return new JsonResponse(['password' => $user->getPassword()]);
    }

  

}
