<?php

namespace App\Security;

use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Core\Exception\InvalidCsrfTokenException;
use Symfony\Component\Security\Core\Exception\UsernameNotFoundException;
use Symfony\Component\Security\Core\Security;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Security\Core\User\UserProviderInterface;
use Symfony\Component\Security\Csrf\CsrfToken;
use Symfony\Component\Security\Csrf\CsrfTokenManagerInterface;
use Symfony\Component\Security\Guard\Authenticator\AbstractFormLoginAuthenticator;
use Symfony\Component\Security\Guard\PasswordAuthenticatedInterface;
use Symfony\Component\Security\Http\Util\TargetPathTrait;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
class LoginFormAuthenticator extends AbstractFormLoginAuthenticator implements PasswordAuthenticatedInterface

{
    use TargetPathTrait;

    public const LOGIN_ROUTE = 'app_login';
    public const LOGIN_ROUTE2 = 'app_flogin';
    private $entityManager;
    private $urlGenerator;
    private $csrfTokenManager;
    private $passwordEncoder;

    public function __construct(EntityManagerInterface $entityManager, UrlGeneratorInterface $urlGenerator, CsrfTokenManagerInterface $csrfTokenManager, UserPasswordEncoderInterface $passwordEncoder,Security $security, SessionInterface $session)
    {
        $this->entityManager = $entityManager;
        $this->urlGenerator = $urlGenerator;
        $this->csrfTokenManager = $csrfTokenManager;
        $this->passwordEncoder = $passwordEncoder;
        $this->security = $security;
        $this->session = $session;
    }

    public function supports(Request $request)
    {
        return self::LOGIN_ROUTE === $request->attributes->get('_route')
            && $request->isMethod('POST');
    }

    public function supports2(Request $request)
    {
        return self::LOGIN_ROUTE2 === $request->attributes->get('_route')
            && $request->isMethod('POST');
    }

    public function getCredentials(Request $request)
    {
        $credentials = [
            'email' => $request->request->get('email'),
            'password' => $request->request->get('password'),
            'csrf_token' => $request->request->get('_csrf_token'),
            'remember_me' => $request->request->get('_remember_me'),
        ];
        $request->getSession()->set(
            Security::LAST_USERNAME,
            $credentials['email']
        );

        return $credentials;
    }

    public function getUser($credentials, UserProviderInterface $userProvider )
    {
        $token = new CsrfToken('authenticate', $credentials['csrf_token']);
        if (!$this->csrfTokenManager->isTokenValid($token)) {
            throw new InvalidCsrfTokenException();
        }

        $user = $this->entityManager->getRepository(User::class)->findOneBy(['email' => $credentials['email']]);

        if (!$user) {
            throw new UsernameNotFoundException('Email could not be found.');
        }

        return $user;
    }

    public function checkCredentials($credentials, UserInterface $user)
    {
      //  return $this->passwordEncoder->isPasswordValid($user, $credentials['password']);
      $plainPassword = $credentials['password'];
        $storedPassword = $user->getPassword();
        return true;
    }

    /**
     * Used to upgrade (rehash) the user's password automatically over time.
     */
    public function getPassword($credentials): ?string
    {
        return $credentials['password'];
    }

    public function onAuthenticationSuccess(Request $request, TokenInterface $token, $providerKey )
    {
       
        $user = $token->getUser();

        if (!$user->getIsEnabled()) {
            $this->session->getFlashBag()->add('warning', 'Votre compte n\'est pas encore activé. Veuillez vérifier votre boîte mail et cliquer sur le lien de confirmation.');
            return new RedirectResponse($this->urlGenerator->generate('app_login'));
        }
    
        if ($targetPath = $this->getTargetPath($request->getSession(), $providerKey)) {
            return new RedirectResponse($targetPath);
        }
    
        $session = $request->getSession();
        $this->session->set('user_id', $user->getIdUser());
        $this->session->set('user_email', $user->getEmail());
        $this->session->get('user_id');
    
        if ($user->getIdRole()->getIdRole() === 1) {
            return new RedirectResponse($this->urlGenerator->generate('app_user_index'));
        } else {
            // Debugging code to print out the user's role
            echo 'User role: ' . $user->getIdRole()->getIdRole();
            return new RedirectResponse($this->urlGenerator->generate('app_user_front'));
        }
    }
    protected function getLoginUrl()
    {
        return $this->urlGenerator->generate(self::LOGIN_ROUTE);
    }
    protected function getLoginUrl2()
    {
        return $this->urlGenerator->generate(self::LOGIN_ROUTE2);
    }
}
