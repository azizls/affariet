<?php

namespace App\Controller;
use App\Entity\User;
use App\Entity\Role;
use App\Entity\Annonce;
use App\Form\AnnonceType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Spipu\Html2Pdf\Html2Pdf;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\UX\Chartjs\Builder\ChartBuilderInterface;
use Symfony\UX\Chartjs\Model\Chart;
use Symfony\Component\Security\Core\Security;

use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\ResponseHeaderBag;


use Symfony\Component\String\Slugger\SluggerInterface;
use Knp\Bundle\SnappyBundle\Snappy\Response\PdfResponse;

use App\Repository\AnnonceRepository;
use App\Controller\Pdf;

use Knp\Component\Pager\PaginatorInterface;








#[Route('/annonce')]
class AnnonceController extends AbstractController
{


    private $managerRegistry;

    public function __construct(ManagerRegistry $managerRegistry)
    {
        $this->managerRegistry = $managerRegistry;
    }

    #[Route('/', name: 'app_annonce_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $annonces = $entityManager
            ->getRepository(Annonce::class)
            ->findAll();

        return $this->render('annonce/index.html.twig', [
            'annonces' => $annonces,
        ]);
       
    }


    #[Route('/front', name: 'app_annonce_indexfront', methods: ['GET'])]
    public function indexfront(EntityManagerInterface $entityManager): Response
    {
        $annonces = $entityManager
            ->getRepository(Annonce::class)
            ->findAll();

        return $this->render('annonce/showfront.html.twig', [
            'annonces' => $annonces,
        ]);
       
    }

    
    #[Route('/new', name: 'app_annonce_new', methods: ['GET', 'POST'])]
public function new(Request $request, EntityManagerInterface $entityManager, SluggerInterface $slugger, Security $security): Response
{
    $user = $security->getUser();

    $annonces = new Annonce();
    $form = $this->createForm(AnnonceType::class, $annonces);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $entityManager->persist($annonces);
        $entityManager->flush();
        
        // récupérer l'ID de l'annonce généré automatiquement
        $idAnnonce = $annonces->getIdAnnonce(); 

        $imageFile = $form->get('image')->getData();

        if ($imageFile) {
            $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
            $safeFilename = $slugger->slug($originalFilename);
            $newFilename = $safeFilename.$imageFile->guessExtension();
            try {
                $imageFile->move(
                    $this->getParameter('image_directory'),
                    $newFilename
                );
            } catch (FileException $e) {
                // Handle the exception if the file cannot be moved
                // ...
            }
            $annonces->setImage($newFilename);
        }

        $annonces->setIdUser($user);
        $entityManager->persist($annonces);
        $entityManager->flush();

        return $this->redirectToRoute('app_annonce_index');
    }

    return $this->render('annonce/new.html.twig', [
        'annonces' => $annonces,
        'form' => $form->createView(),
    ]);
}


    
    
    
    


    /**
 * @Route("/annonce/{idAnnonce}", name="app_annonce_show")
 */
public function show(Annonce $annonce): Response
{
    return $this->render('annonce/show.html.twig', [
        'annonce' => $annonce,
    ]);
}
    

#[Route('/{idAnnonce}/edit', name: 'app_annonce_edit', methods: ['GET', 'POST'])]
public function edit(Request $request, Annonce $annonce, EntityManagerInterface $entityManager): Response
{
    $form = $this->createForm(AnnonceType::class, $annonce);
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        $annonce = $form->getData();
        $annonce->setDateAnnonce(new \DateTime());

        $entityManager->persist($annonce); // Enregistrement des modifications
        $entityManager->flush();

        $this->addFlash('success', 'Annonce modifiée avec succès.');

        return $this->redirectToRoute('app_annonce_show', ['idAnnonce' => $annonce->getIdAnnonce()]);
    }

    return $this->render('annonce/edit.html.twig', [
        'annonce' => $annonce,
        'form' => $form->createView(),
    ]);
}


    
    

 /**
 * @Route("/annonce/{idAnnonce}/delete", name="app_annonce_delete")
 */
public function delete(Request $request, Annonce $annonce, EntityManagerInterface $entityManager): Response
{
    $entityManager->remove($annonce);
    $entityManager->flush();

    return $this->redirectToRoute('app_annonce_index');
}


 /**
 * @Route("/annonce/{idAnnonce}/deletefront", name="app_annonce_deletefront")
 */
public function deletefront(Request $request, Annonce $annonce, EntityManagerInterface $entityManager): Response
{
    $entityManager->remove($annonce);
    $entityManager->flush();

    return $this->redirectToRoute('app_annonce_indexfront');
}


/**
 * @Route("/annonce/{idAnnonce}/afficherCommentaires", name="app_annonce_commentaires")
 */
public function afficherCommentaires(int $idAnnonce, AnnonceRepository $annonceRepository,  EntityManagerInterface $entityManager, Security $security): Response
{
    $user = $security->getUser();
    // Récupérez l'annonce correspondante à partir de son identifiant
    $annonces = $annonceRepository->find($idAnnonce);

    // Récupérez tous les commentaires associés à l'annonce
    $commentaires = $annonces->getCommentaire();

    // Passez les commentaires et l'annonce à la vue pour les afficher
    return $this->render('commentaire/show.html.twig', [
        'annonce' => $annonces,
        'commentaires' => $commentaires,
    ]);
}


/**
 * @Route("/annonce/{idAnnonce}/afficherCommentairesfront", name="app_annonce_commentairesfront")
 */
public function afficherCommentairesfront(int $idAnnonce, AnnonceRepository $annonceRepository): Response
{
    $annonces = new Annonce();
    $annonces = $annonceRepository->find($idAnnonce);

    // Récupérez tous les commentaires associés à l'annonce
    $commentaires = $annonces->getCommentaire();

    // Passez les commentaires et l'annonce à la vue pour les afficher
    return $this->render('commentaire/commentaires_front.html.twig', [
        'annonce' => $annonces,
        'commentaires' => $commentaires,
    ]);
    
}

public function indexpagination(Request $request, PaginatorInterface $paginator)
{
    $entityManager = $this->getDoctrine()->getManager();

    $query = $entityManager->createQuery(
        'SELECT a, c
        FROM App\Entity\Annonce a
        LEFT JOIN a.commentaires c
        ORDER BY a.dateAnnonce DESC'
    );

    $pagination = $paginator->paginate(
        $query,
        $request->query->getInt('page', 1),
        10 // nombre d'annonces par page
    );

    return $this->render('annonce/showfront.html.twig', [
        'pagination' => $pagination,
    ]);
}



/**
 * @Route("/annonces/search", name="app_annonce_search")
 */
public function search(Request $request, EntityManagerInterface $entityManager): Response
{
    $queryBuilder = $entityManager->createQueryBuilder();

    $queryBuilder->select('a')
        ->from(Annonce::class, 'a');
        
        $description = $request->query->get('description');
    if ($description) {
        $queryBuilder->andWhere('a.description LIKE :description')
            ->setParameter('description', '%'.$description.'%');
    }

    $type = $request->query->get('type');
    if ($type) {
        $queryBuilder->andWhere('a.type = :type')
            ->setParameter('type', $type);
    }

    $annonces = $queryBuilder->getQuery()->getResult();

    return $this->render('annonce/search.html.twig', [
        'annonces' => $annonces,
    ]);
}


/**
 * @Route("/annonces/date", name="app_annonce_date")
 */
public function sortByDate(EntityManagerInterface $entityManager): Response
{
    $annonces = $entityManager->getRepository(Annonce::class)->findBy([], ['dateAnnonce' => 'DESC']);

    return $this->render('annonce/date.html.twig', [
        'annonces' => $annonces,
    ]);
}

#[Route('/post', name: 'app_annonce_post', methods: ['GET', 'POST'])]
    public function post(Request $request, EntityManagerInterface $entityManager, SluggerInterface $slugger, Security $security): Response
    {
        $user = $security->getUser();

        $annonces = new Annonce();
        $form = $this->createForm(AnnonceType::class, $annonces);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($annonces);
            $entityManager->flush();
            
            // récupérer l'ID de l'annonce généré automatiquement
            $idAnnonce = $annonces->getIdAnnonce(); 
    
            $imageFile = $form->get('image')->getData();
    
            if ($imageFile) {
                $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
                $safeFilename = $slugger->slug($originalFilename);
                $newFilename = $safeFilename.'.'.$imageFile->guessExtension();
                try {
                    $imageFile->move(
                        $this->getParameter('image_directory'),
                        $newFilename
                    );
                } catch (FileException $e) {
                    // Handle the exception if the file cannot be moved
                    // ...
                }
                $annonces->setImage($newFilename);
            }
    
            $annonces->setIdUser($user);
            $entityManager->persist($annonces);
            $entityManager->flush();
            return $this->redirectToRoute('app_annonce_indexfront');
        }
    
        return $this->render('annonce/post.html.twig', [
            'annonces' => $annonces,
            'form' => $form->createView(),
        ]);
    }

    #[Route('/mesannonces', name: 'app_mesannonces_index', methods: ['GET'])]
    public function front_index(EntityManagerInterface $entityManager, Security $security): Response
    {
        $user = $security->getUser();
        $annonce = $this->getDoctrine()->getRepository(Annonce::class)->findBy(['idUser' => $user->getIdUser()]);
        
        // Rendre la vue avec les annonces de l'utilisateur
        return $this->render('/annonce/mes_annonces.html.twig', [
            'annonce' => $annonce,
        ]);
    }

    #[Route('/annonces/pdf', name: 'app_annonce_pdf', methods: ['GET'])]
    public function generatePdf(Request $request, EntityManagerInterface $entityManager, \Knp\Snappy\Pdf $pdf)
    {
        // Récupérer les annonces de la base de données
        $annonces = $entityManager->getRepository(Annonce::class)->findAll();
    
        // Générer le contenu HTML
        $html = $this->renderView('annonce/pdf_export.html.twig', [
            'annonces' => $annonces,
        ]);
    
        // Générer le PDF
        $filename = sprintf('%s/annonces_%s.pdf', $this->getParameter('kernel.project_dir') . '/public', date('Y-m-d_H-i-s'));
        $response = new PdfResponse(
            $pdf->getOutputFromHtml($html),
            $filename
        );
    
        // Ajouter les headers pour télécharger le fichier PDF
        $disposition = $response->headers->makeDisposition(
            ResponseHeaderBag::DISPOSITION_ATTACHMENT,
            $filename
        );
        $response->headers->set('Content-Disposition', $disposition);
    
        return $response;
    }


   


 /**
 * @Route("/stat", name="app_annonce_stat")
 */
public function stat(EntityManagerInterface $em): Response
{
    $stats = $em->createQueryBuilder()
        ->select('a.type, COUNT(a.idAnnonce) as count')
        ->from('App\Entity\Annonce', 'a')
        ->groupBy('a.type')
        ->getQuery()
        ->getResult();

    return $this->render('annonce/stat.html.twig', [
        'stats' => $stats,
    ]);
}


/**
 * @Route("/annonces/recherche", name="annonces_recherche")
 */
public function recherche(Request $request, AnnonceRepository $annonceRepository)
{
    $search = $request->query->get('search');
    $type = $request->query->get('type');

    $annonces = $annonceRepository->rechercheMulticritere($search, $type);

    return $this->render('annonce/recherche.html.twig', [
        'annonces' => $annonces,
        'search' => $search,
        'type' => $type,
    ]);
}


public function annonceList(Request $request)
{
    $sort = $request->query->get('sort');
    $annonces = array();

    if ($sort == 'most_liked') {
        $annonces = $this->getDoctrine()->getRepository(Annonce::class)->findBy(array(), array('nblikes' => 'DESC'));
    } elseif ($sort == 'most_disliked') {
        $annonces = $this->getDoctrine()->getRepository(Annonce::class)->findBy(array(), array('nbdislikes' => 'DESC'));
    } else {
        $annonces = $this->getDoctrine()->getRepository(Annonce::class)->findAll();
    }

    return $this->render('annonce/showfront.html.twig', array(
        'annonces' => $annonces
    ));
}



public function stat1(ChartBuilderInterface $chartBuilder, EntityManagerInterface $entityManager): Response
{
    $type1Count = 0;
    $type2Count = 0;
    $type3Count = 0;

    $annonces = $entityManager->getRepository(Annonce::class)->findAll();

    foreach ($annonces as $annonce) {
        $type = $annonce->getType();
        switch ($type) {
            case 'type1':
                $type1Count++;
                break;
            case 'type2':
                $type2Count++;
                break;
            case 'type3':
                $type3Count++;
                break;
            default:
                break;
        }
    }

    $chart = $chartBuilder->createChart(Chart::TYPE_BAR);

    $chart->setData([
        'labels' => ['Type 1', 'Type 2', 'Type 3'],
        'datasets' => [
            [
                'label' => 'Nombre d\'annonces',
                'backgroundColor' => ['#007bff', '#28a745', '#dc3545'],
                'borderColor' => ['#007bff', '#28a745', '#dc3545'],
                'data' => [$type1Count, $type2Count, $type3Count],
            ],
        ],
    ]);

    $chart->setOptions([
        'scales' => [
            'y' => [
                'suggestedMin' => 0,
                'suggestedMax' => count($annonces),
            ],
        ],
    ]);

    return $this->render('annonce/stat.html.twig', [
        'chart' => $chart,
    ]);
}



}
