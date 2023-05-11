<?php

namespace App\Controller;

use App\Entity\Role;
use App\Entity\User;
use App\Form\UserType;
use App\Form\ConfirmRegistrationType;
use App\Repository\UserRepository;
use Symfony\Component\HttpFoundation\Response;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Swift_Mailer;
use Swift_Message;
use Swift_SmtpTransport;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Security\Csrf\TokenGenerator\TokenGeneratorInterface;
use Symfony\Component\String\Slugger\SluggerInterface;

class RegistrationController extends AbstractController
{
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }

/**
 * @Route("/register", name="register")
 */
public function register(Request $request, UserPasswordEncoderInterface $passwordEncoder, Swift_Mailer $mailer, TokenGeneratorInterface $tokenGenerator, UrlGeneratorInterface $urlGenerator, SluggerInterface $slugger): Response
{
    $user = new User();
    $form = $this->createForm(UserType::class, $user);

    $form->handleRequest($request);
    if ($form->isSubmitted() && $form->isValid()) {
        // handle file upload
        $imageFile = $form->get('image')->getData();
$email =$user->getEmail();
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

        // Generate confirmation token
        $confirmationToken = $tokenGenerator->generateToken();
        $user->setConfirmationToken($confirmationToken);

        $user->setIsEnabled(0);
        // Encode password
        
        //$password = $passwordEncoder->encodePassword($user, $user->getMdp());
        //$user->setMdp($password);
        $password = $user->getMdp(); // get the plain-text password
        $user->setMdp($password);

        // Set default role
        $role = $this->entityManager->getRepository(Role::class)->find(2);
        $user->setIdRole($role);

        // Save user
        $this->entityManager->persist($user);
        $this->entityManager->flush();

        // Send confirmation email
        $url = $urlGenerator->generate('app_confirm_registration', ['token' => $confirmationToken], UrlGeneratorInterface::ABSOLUTE_URL);
       
        // Create a new SMTP transport with the desired configuration
        $transport = new Swift_SmtpTransport('smtp.gmail.com', 587, 'tls');
        $transport->setUsername('ahmedturki2662@gmail.com');
        $transport->setPassword('uvnfkhytnjpaffpv');

        // Create a new mailer instance with the transport object
        $mailer = new Swift_Mailer($transport);
       
        $message = (new Swift_Message('Confirmation de votre inscription'))
            ->setFrom('ahmedturki2662@gmail.com')
            ->setTo($user->getEmail())
            ->setBody(
                $this->renderView(
                    'email/confirm_registration.html.twig',
                    ['url' => $url, 'user' => $user]
                ),
                'text/html'
            );
        $mailer->send($message);

        // Redirect to success page
        return $this->redirectToRoute('app_login');
    }

    return $this->render('registration/register.html.twig', [
        'form' => $form->createView(),
    ]);
}

    /**
 * @Route("/confirm-registration/{token}", name="app_confirm_registration")
 */
public function confirmRegistration(Request $request, string $token): Response
{
    $userRepository = $this->getDoctrine()->getRepository(User::class);
    $user = $userRepository->findOneBy(['confirmation_token' => $token]);

    if (!$user) {
        throw $this->createNotFoundException('Lien de confirmation invalide');
    }

    $user->setIsEnabled(1);
   // $user->setConfirmationToken(null);

    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($user);
    $entityManager->flush();

    $this->addFlash('success', 'Votre compte a été confirmé avec succès');

    return $this->redirectToRoute('app_login');
}
}