<?php

namespace App\Controller;
use App\Entity\Annonce;
use App\Entity\Commentaire;
use App\Form\CommentaireType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use App\Entity\User;
use Symfony\Component\Security\Core\Security;

#[Route('/commentaire')]
class CommentaireController extends AbstractController
{
    #[Route('/', name: 'app_commentaire_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $commentaires = $entityManager
            ->getRepository(Commentaire::class)
            ->findAll();

        return $this->render('commentaire/index.html.twig', [
            'commentaires' => $commentaires,
        ]);
    }

  /**
 * @Route("/annonce/{idAnnonce}/new", name="app_commentaire_new")
 */
public function new(Request $request, EntityManagerInterface $entityManager, $idAnnonce , Security $security)
{  $user = $security->getUser(); 
    // Récupérez l'annonce correspondante à partir de son identifiant
    $annonce = $entityManager->getRepository(Annonce::class)->find($idAnnonce);

    // Créez un nouveau commentaire
    $commentaire = new Commentaire();

    // Ajoutez l'annonce au commentaire
    $commentaire->setAnnonce($annonce);

    // Ajoutez l'identifiant de l'utilisateur au commentaire (ici, il est défini comme statique)
    $user = $this->getUser();
$commentaire->setIdUser1($user);


    // Créez un formulaire de création de commentaire
    $form = $this->createForm(CommentaireType::class, $commentaire);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        // Liez le commentaire à l'annonce et à l'utilisateur
        $commentaire->setAnnonce($annonce);
        
        $commentaire->setIdUser1($user);
        // Enregistrez le commentaire dans la base de données
        $entityManager->persist($commentaire);
        $entityManager->flush();

        // Redirigez l'utilisateur vers la page de l'annonce
        return $this->redirectToRoute('app_annonce_show', ['idAnnonce' => $annonce->getIdAnnonce()]);
    }

    return $this->render('commentaire/new.html.twig', [
        'form' => $form->createView(),
        'commentaire' => $commentaire,
        'annonce' => $annonce,
        

    ]);
}



    #[Route('/{idCommentaire}', name: 'app_commentaire_show', methods: ['GET'])]
    public function show(Commentaire $commentaire): Response
    {
        return $this->render('commentaire/show.html.twig', [
            'commentaire' => $commentaire,
        ]);
    }

    #[Route('/{idCommentaire}/edit', name: 'app_commentaire_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Commentaire $commentaire, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(CommentaireType::class, $commentaire);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_annonce_indexfront', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('commentaire/edit.html.twig', [
            'commentaire' => $commentaire,
            'form' => $form,
        ]);
    }

    #[Route('/{idCommentaire}', name: 'app_commentaire_delete', methods: ['POST'])]
    public function delete(Request $request, Commentaire $commentaire, EntityManagerInterface $entityManager, Security $security): Response
    {
        $user = $security->getUser(); 
        $user = $entityManager->getRepository(User::class)->findOneBy(['idUser' => $user->getIdUser()]);
        if ($this->isCsrfTokenValid('delete'.$commentaire->getIdCommentaire(), $request->request->get('_token'))) {
            $entityManager->remove($commentaire);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_commentaire_index', [], Response::HTTP_SEE_OTHER);
    }


/**
 * @Route("/annonce/{idAnnonce}/new", name="app_commentaire_commenter")
 */
public function commenter(Request $request, EntityManagerInterface $entityManager, $idAnnonce, Security $security)
{
    $user = $security->getUser(); 
    
    $annonce = $entityManager->getRepository(Annonce::class)->find($idAnnonce);

    $commentaire = new Commentaire(); 
    $commentaire->setAnnonce($annonce);

    $form = $this->createForm(CommentaireType::class, $commentaire);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
       
        $commentaire = $form->getData(); // Récupération de l'entité Commentaire remplie avec les données soumises par le formulaire
        
        $commentaire->setAnnonce($annonce);        
        $commentaire->setIdUser1($user);
       
        $entityManager->persist($commentaire);
        $entityManager->flush();

        // Redirection vers la page d'affichage de l'annonce
        return $this->redirectToRoute('app_annonce_indexfront', ['idAnnonce' => $annonce->getIdAnnonce()]);
    }

    return $this->render('commentaire/commenter.html.twig', [
        'form' => $form->createView(),
        'annonces' => $annonce,
    ]);
}





/**
 * @Route("/commentaire/{idCommentaire}/deletecommentaire", name="app_commentaire_deletecomment")
 */
public function deleteCommentaire(Request $request, Commentaire $commentaire, EntityManagerInterface $entityManager , Security $security): Response
{
{
    $user = $security->getUser(); 
    // Vérifier si l'utilisateur connecté est le propriétaire du commentaire
    if ($user !== $commentaire->getIdUser1()) {
        throw $this->createAccessDeniedException('Vous n\'êtes pas autorisé à supprimer ce commentaire.');
    }

    $entityManager->remove($commentaire);
    $entityManager->flush();

   
    $this->addFlash('success', 'Commentaire supprimé avec succès.');

 
    return $this->redirectToRoute('app_annonce_indexfront');
    
}


}


/**
 * @Route("/commentaire/commenter/ajax/{idAnnonce}", name="app_commentaire_commenter_ajax", methods={"POST"})
 */
public function commenterAjax(Request $request, Annonce $annonce, EntityManagerInterface $entityManager , Security $security): Response
{
    $user = $security->getUser(); 
    $commentaire = new Commentaire();
    $commentaire->setAnnonce($annonce);
    $commentaire->setIdUser1($user);
    $commentaire->setComment($request->request->get('commentaire'));

    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($commentaire);

    $errors = array();

    // Vérifier si le commentaire est vide
    if (empty($commentaire->getComment())) {
        $errors[] = 'Le commentaire ne peut pas être vide.';
    }

    // Vérifier si le commentaire contient des mots interdits
    $badwords = ['fuck', 'motherfucker', 'shit'];
    foreach ($badwords as $badword) {
        if (stripos($commentaire->getComment(), $badword) !== false) {
            $errors[] = 'Le commentaire contient des mots interdits.';
        }
    }

    if (!empty($errors)) {
        $entityManager->detach($commentaire);
        $this->addFlash('error', implode('<br>', $errors));
        return $this->redirect($request->headers->get('referer'));
    }

    $entityManager->flush();

    $this->addFlash('success', 'Le commentaire a été ajouté avec succès.');
    return $this->redirect($request->headers->get('referer'));
}







}