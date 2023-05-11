<?php

namespace App\Controller;

use App\Entity\User;
use App\Repository\UserRepository;
use App\Form\UserType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Security;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\String\Slugger\SluggerInterface;



#[Route('/user')]
class UserController extends AbstractController
{
    #[Route('/back', name: 'app_user_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $users = $entityManager
            ->getRepository(User::class)
            ->findAll();

        return $this->render('user/index.html.twig', [
            'users' => $users,
        ]);
    }

    #[Route('/', name: 'app_user_front', methods: ['GET'])]
    public function front(EntityManagerInterface $entityManager): Response
    {
        $users = $entityManager
            ->getRepository(User::class)
            ->findAll();

        return $this->render('front.html.twig', [
            'users' => $users,
        ]);
    }

    #[Route('/new', name: 'app_user_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager, SluggerInterface $slugger): Response
    {
        $user = new User();
    $form = $this->createForm(UserType::class, $user);
    // Gérez la soumission du formulaire
    $form->handleRequest($request);
    if ($form->isSubmitted() && $form->isValid()) {
        // Le formulaire est valide, continuez le traitement 
       // $email = $user->getEmail(); 
        $data = $form->getData();
    }
   
    

    if ($form->isSubmitted() && $form->isValid()) {
        // handle file upload
        $imageFile = $form->get('image')->getData();
        $email = $user->getEmail(); 


        if ($imageFile) {
            $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
            $safeFilename = $slugger->slug($originalFilename);
            $newFilename = $email.'-'.$safeFilename.'-'.uniqid().'.'.$imageFile->guessExtension();

            // move the file to the user_images_directory
            $imageFile->move(
                $this->getParameter('user_images_directory'),
                $newFilename
            );
 // update the 'image' property to store the file name
            $user->setImage($newFilename);
           
        }

        $entityManager->persist($user);
        $entityManager->flush();

        return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
    }

    return $this->renderForm('user/new.html.twig', [
        'user' => $user,
        'form' => $form,
    ]);
    }

    #[Route('/{idUser}', name: 'app_user_show', methods: ['GET'])]
    public function show(User $user): Response
    {
        return $this->render('user/show.html.twig', [
            'user' => $user,
        ]);
    }

    #[Route('/{idUser}/edit', name: 'app_user_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, User $user, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('user/edit.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
    }

   #[Route('/edit/{user_id}', name: 'app_user_edit2', methods: ['GET', 'POST'])]
public function edit2(Request $request, EntityManagerInterface $entityManager, Security $security, UserPasswordEncoderInterface $encoder, $user_id): Response
{
    dump($user_id); // add this line to debug
    $user = $entityManager->getRepository(User::class)->find($user_id);
    if (!$user) {
        throw $this->createNotFoundException('User not found');
    }
    
        // make sure the user is the one who is authenticated
        $authenticatedUser = $security->getUser();
        if ($authenticatedUser !== $user) {
            throw $this->createNotFoundException('User not found');
        }
    
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            // hash the password before saving
           // $password = $encoder->encodePassword($user, $user->getMdp());
           //$user->setMdp($password);
           $password = $user->getMdp(); // get the plain-text password
           $user->setMdp($password);
          // handle file upload
            $imageFile = $form->get('image')->getData();

            if ($imageFile) {
                // generate a unique filename
                $newFilename = uniqid().'.'.$imageFile->guessExtension();

                // move the file to the user_images_directory
                $imageFile->move(
                    $this->getParameter('user_images_directory'),
                    $newFilename
                );

                // update the 'image' property to store the file name
                $user->setImage($newFilename);
            }


            $entityManager->flush();
    
            return $this->redirectToRoute('app_user_front', [], Response::HTTP_SEE_OTHER);
          
       
        }
    
        return $this->renderForm('registration/modif.html.twig', [
            'user' => $user,
            'form' => $form,
        ]);
}


#[Route('/user/show', name: 'app_user_show2')]
public function show2(Request $request, EntityManagerInterface $entityManager): Response
{
    $session = $request->getSession();
    $user_id = $session->get('user_id');

    $user = $entityManager->getRepository(User::class)->find($user_id);
    if (!$user) {
        throw $this->createNotFoundException('User not found');
    }

    return $this->render('user/show.html.twig', [
        'user' => $user,
    ]);
}

public function searchUsers(Request $request): Response
{
    $searchQuery = $request->request->get('query');
    $userRepository = $this->getDoctrine()->getRepository(User::class);
    $searchResults = $userRepository->findOneBy($searchQuery);

    // render the search results
    return $this->render('user/index.html.twig', [
        'user' => $user,
        'searchQuery' => $searchQuery,
        'searchResults' => $searchResults,
    ]);
}

    #[Route('/{idUser}', name: 'app_user_delete', methods: ['POST'])]
    public function delete(Request $request, User $user, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$user->getIdUser(), $request->request->get('_token'))) {
            $entityManager->remove($user);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_user_index', [], Response::HTTP_SEE_OTHER);
    }

    
    #[Route('/AllUsers/status', name: 'list_users_jz')]
    public function readUserjson(SerializerInterface $normalizer, EntityManagerInterface $entityManager): Response
    {
        $user = new User();
        $user = $entityManager
        ->getRepository(User::class)
        ->findAll();
        
        
        $UserNormalises=$normalizer->serialize($user,'json',['groups'=>'post:read']);
        $json=json_encode($UserNormalises);
       
    
        return new Response($json);
    }
 

}
