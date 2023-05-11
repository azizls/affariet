<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Symfony\Component\HttpFoundation\Request;
use App\Repository\UserRepository;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use App\Form\ForgotPasswordType;
use App\Form\ResetPasswordType;
use Swift_Mailer;
use Swift_Message;
use Swift_SmtpTransport;
use App\Entity\User;
use App\Entity\Role;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use League\OAuth2\Client\Provider\Github;
use League\OAuth2\Client\Provider\Facebook;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\Security\Core\Authentication\Token\UsernamePasswordToken;
use Symfony\Component\Security\Http\Event\InteractiveLoginEvent;
use Symfony\Component\Security\Core\Authentication\Token\Storage\TokenStorageInterface;
use Symfony\Component\EventDispatcher\EventDispatcherInterface;



class SecurityController extends AbstractController
{
    private $tokenStorage;
    private $eventDispatcher;

    #[Route('/security', name: 'app_security')]
    public function index(): Response
    {
        return $this->render('security/index.html.twig', [
            'controller_name' => 'SecurityController',
        ]);
    }
    public function __construct(UrlGeneratorInterface $urlGenerator,TokenStorageInterface $tokenStorage,EventDispatcherInterface $eventDispatcher,EntityManagerInterface $entityManager)
    {
       $this->provider=new Facebook([
         'clientId'          => $_ENV['FCB_ID'],
         'clientSecret'      => $_ENV['FCB_SECRET'],
         'redirectUri'       => $_ENV['FCB_CALLBACK'],
         'graphApiVersion'   => 'v15.0',
     ]);
     $this->urlGenerator = $urlGenerator;
     $this->tokenStorage = $tokenStorage;
     $this->eventDispatcher = $eventDispatcher;
     $this->entityManager = $entityManager;
    }

    #[Route(path: '/login', name: 'app_login')]
    public function login(AuthenticationUtils $authenticationUtils): Response
    {
        // if ($this->getUser()) {
        //     return $this->redirectToRoute('target_path');
        // }

        // get the login error if there is one
        $error = $authenticationUtils->getLastAuthenticationError();
        // last username entered by the user
        $lastUsername = $authenticationUtils->getLastUsername();

        return $this->render('security/login.html.twig', ['last_username' => $lastUsername, 'error' => $error]);
    }

    #[Route(path: 'back/login', name: 'app_flogin')]
    public function login2(AuthenticationUtils $authenticationUtils): Response
    {
        // if ($this->getUser()) {
        //     return $this->redirectToRoute('target_path');
        // }

        // get the login error if there is one
        $error = $authenticationUtils->getLastAuthenticationError();
        // last username entered by the user
        $lastUsername = $authenticationUtils->getLastUsername();

        return $this->render('security/frontlogin.html.twig', ['last_username' => $lastUsername, 'error' => $error]);
    }

    #[Route(path: '/logout', name: 'app_logout')]
    public function logout(): void
    {
        throw new \LogicException('This method can be blank - it will be intercepted by the logout key on your firewall.');
    }

    
    #[Route(path: '/forgot', name: 'forgot')]
    public function forgotPassword(Request $request, UserRepository $userRepository,Swift_Mailer $mailer, TokenGeneratorInterface  $tokenGenerator)
    {


        $form = $this->createForm(ForgotPasswordType::class);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {
            $donnees = $form->getData();//


            $user = $userRepository->findOneBy(['email'=>$donnees]);
            if(!$user) {
                $this->addFlash('danger','cette adresse n\'existe pas');
                return $this->redirectToRoute("forgot");

            }
            $token = $tokenGenerator->generateToken();

            try{
                $user->setResetToken($token);
                $entityManger = $this->getDoctrine()->getManager();
                $entityManger->persist($user);
                $entityManger->flush();




            }catch(\Exception $exception) {
                $this->addFlash('warning','une erreur est survenue :'.$exception->getMessage());
                return $this->redirectToRoute("app_login");


            }

            $url = $this->generateUrl('app_reset_password',array('token'=>$token),UrlGeneratorInterface::ABSOLUTE_URL);

             // Create a new SMTP transport with the desired configuration
             $transport = new Swift_SmtpTransport('smtp.gmail.com', 587, 'tls');
                $transport->setUsername('ahmedturki2662@gmail.com');
                $transport->setPassword('uvnfkhytnjpaffpv');

        // Create a new mailer instance with the transport object
        $mailer = new Swift_Mailer($transport);


            //BUNDLE MAILER
            $message = (new Swift_Message('Mot de password oublié'))
                ->setFrom('ahmedturki2662@gmail.com')
                ->setTo($user->getEmail())
                ->setBody("<p> Bonjour</p> unde demande de réinitialisation de mot de passe a été effectuée. Veuillez cliquer sur le lien suivant :".$url,
                "text/html");

            //send mail
            $mailer->send($message);
            $this->addFlash('message','E-mail  de réinitialisation du mp envoyé :');
        //    return $this->redirectToRoute("app_login");



        }

        return $this->render("security/forgotPassword.html.twig",['form'=>$form->createView()]);
    }


    /**
     * @Route("/resetpassword/{token}", name="app_reset_password")
     */
    public function resetpassword(Request $request,string $token, UserPasswordEncoderInterface  $passwordEncoder)
    {
        $user = $this->getDoctrine()->getRepository(User::class)->findOneBy(['reset_token'=>$token]);

        if($user == null ) {
            $this->addFlash('danger','TOKEN INCONNU');
            return $this->redirectToRoute("app_login");

        }

        if($request->isMethod('POST')) {
            $user->setResetToken(null);

            $user->setMdp($request->request->get('password'));
           
            $entityManger = $this->getDoctrine()->getManager();
            $entityManger->persist($user);
            $entityManger->flush();

            $this->addFlash('message','Mot de passe mis à jour :');
            return $this->redirectToRoute("app_login");

        }
        else {
            return $this->render("security/resetPassword.html.twig",['token'=>$token]);

        }
    }


    #[Route('/fcb-login', name: 'fcb_login')]
    public function fcbLogin(): Response
    {
         
        $helper_url=$this->provider->getAuthorizationUrl();

        return $this->redirect($helper_url);
    }


    #[Route('/fcb-callback', name: 'fcb_callback')]
public function fcbCallBack(UserRepository $userDb, EntityManagerInterface $manager,UrlGeneratorInterface $urlGenerator, Request $request): Response
{
    //Récupérer le token
    $token = $this->provider->getAccessToken('authorization_code', [
        'code' => $_GET['code']
    ]);

    try {
        //Récupérer les informations de l'utilisateur
        $user = $this->provider->getResourceOwner($token);
        $user = $user->toArray();
        $email = $user['email'];
        $nom = $user['name'];
        $picture = [$user['picture_url']];

        //Vérifier si l'utilisateur existe dans la base des données
        $user_exist = $userDb->findOneByEmail($email);

        if ($user_exist) {
            $user_exist->setNom($nom)
                ->setPictureUrl($picture);
            $manager->flush();

            // Create a new user token
            $token = new UsernamePasswordToken($user_exist, null, 'main', $user_exist->getRoles());

            // Authenticate the user
            $this->tokenStorage->setToken($token);

            // Fire the login event
            $event = new InteractiveLoginEvent($request, $token);
            $this->eventDispatcher->dispatch($event);

            return new RedirectResponse($urlGenerator->generate('app_user_front'));
        } else {
            $role = new Role () ;
            $new_user = new User();
            $new_user->setNom($nom)
                ->setEmail($email)
                ->setMdp(sha1(str_shuffle('abscdop123390hHHH;:::OOOI')))
                ->setPictureUrl($picture);
                $new_user->setIsEnabled(1);
                $role = $this->entityManager->getRepository(Role::class)->find(2);
                $new_user->setIdRole($role);
            $manager->persist($new_user);
            $manager->flush();

            // Create a new user token
            $token = new UsernamePasswordToken($new_user, null, 'main', $new_user->getRoles());

            // Authenticate the user
            $this->tokenStorage->setToken($token);

            // Fire the login event
            $event = new InteractiveLoginEvent($request, $token);
            $this->eventDispatcher->dispatch($event);

            return new RedirectResponse($urlGenerator->generate('app_user_front'));
        }
    } catch (\Throwable $th) {
        return new Response($th->getMessage());
    }
}

}
