<?php

namespace App\Controller;
use App\Entity\User;
use App\Entity\Annonce;
use App\Entity\Reactions;
use App\Form\ReactionsType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Security;

#[Route('/reactions')]
class ReactionsController extends AbstractController
{
    #[Route('/', name: 'app_reactions_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $reactions = $entityManager
            ->getRepository(Reactions::class)
            ->findAll();

        return $this->render('reactions/index.html.twig', [
            'reactions' => $reactions,
        ]);
    }

    #[Route('/new', name: 'app_reactions_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $reaction = new Reactions();
        $form = $this->createForm(ReactionsType::class, $reaction);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($reaction);
            $entityManager->flush();

            return $this->redirectToRoute('app_reactions_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reactions/new.html.twig', [
            'reaction' => $reaction,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_reactions_show', methods: ['GET'])]
    public function show(Reactions $reaction): Response
    {
        return $this->render('reactions/show.html.twig', [
            'reaction' => $reaction,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_reactions_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Reactions $reaction, EntityManagerInterface $entityManager): Response
    {
        
        $form = $this->createForm(ReactionsType::class, $reaction);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
           
            $entityManager->flush();

            return $this->redirectToRoute('app_reactions_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('reactions/edit.html.twig', [
            
            'reaction' => $reaction,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_reactions_delete', methods: ['POST'])]
    public function delete(Request $request, Reactions $reaction, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reaction->getId(), $request->request->get('_token'))) {
            $entityManager->remove($reaction);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_reactions_index', [], Response::HTTP_SEE_OTHER);
    }
    
    #[Route('/like/{id}', name: 'app_reactions_like', methods:['GET', 'POST'])]
    public function like(Annonce $annonce, EntityManagerInterface $entityManager, Request $request, Security $security): Response
    {
        $user = $security->getUser(); 
        $existingReaction = $entityManager->getRepository(Reactions::class)->findOneBy(['idAnnonce' => $annonce->getIdAnnonce(), 'idUser' => $user->getIdUser()]);
    
        if ($request->isMethod('POST')) {
            if (!$existingReaction) {
                // L'utilisateur n'a pas encore réagi à cette annonce avec un like
                $reaction = new Reactions();
                $reaction->setTypeReact('like');
                $reaction->setIdAnnonce($annonce);
                $reaction->setIdUser($user);
                $entityManager->persist($reaction);
                $annonce->setNbLikes($annonce->getNbLikes() + 1);
                $entityManager->flush();
                $this->addFlash('success', 'Vous avez réagi avec un like.');
            } elseif ($existingReaction->getTypeReact() == 'like') {
                // L'utilisateur a déjà réagi avec un like, donc on supprime la réaction et on décrémente le nombre de likes
                $entityManager->remove($existingReaction);
                $annonce->setNbLikes(max(0, $annonce->getNbLikes() - 1));
                $entityManager->flush();
                $this->addFlash('warning', 'Vous avez retiré votre like.');
            }
        }
    
        return $this->redirect($_SERVER['HTTP_REFERER']);
    }
    
    
    
        
    #[Route('/dislike/{id}', name: 'app_reactions_dislike', methods:['POST'])]
    public function dislike(Annonce $annonce, EntityManagerInterface $entityManager, Request $request, Security $security): Response
    {
        $user = $security->getUser(); 

        $existingReaction = $entityManager->getRepository(Reactions::class)->findOneBy(['idAnnonce' => $annonce->getIdAnnonce(), 'idUser' => $user->getIdUser()]);
    
        if ($request->isMethod('POST')) {
            if ($existingReaction && $existingReaction->getTypeReact() == 'dislike') {
                $entityManager->remove($existingReaction);
                $annonce->setNbDislikes(max(0, $annonce->getNbDislikes() - 1));
                $entityManager->flush();
                $this->addFlash('warning', 'Vous avez retiré votre dislike.');
            } else {
                if ($existingReaction && $existingReaction->getTypeReact() == 'like') {
                    $entityManager->remove($existingReaction);
                    $annonce->setNbLikes(max(0, $annonce->getNbDislikes() + 1));
                    $annonce->setNbLikes(max(0, $annonce->getNbLikes() - 1));
                }
    
                $reaction = new Reactions();
                $reaction->setTypeReact('dislike');
                $reaction->setIdAnnonce($annonce);
                $reaction->setIdUser($user);
                $entityManager->persist($reaction);
                $annonce->setNbDislikes($annonce->getNbDislikes() + 1);
                $entityManager->flush();
    
    
                $this->addFlash('success', 'Vous avez réagi avec un dislike.');
            }
        }
    
        return $this->redirect($_SERVER['HTTP_REFERER']);

    }
    
    
    

}
