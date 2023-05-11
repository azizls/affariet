<?php

namespace App\Controller;

use App\Entity\Annonce;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;

use Doctrine\Persistence\ManagerRegistry;

class PdfGeneratorController extends AbstractController
{
    private $managerRegistry;

    public function __construct(ManagerRegistry $managerRegistry)
    {
        $this->managerRegistry = $managerRegistry;
    }

   /**
 * @Route("/pdf/generator/", name="pdf_generator")
 */
public function index(): Response
{
    $annonces = $this->managerRegistry->getRepository(Annonce::class)->findAll();

    $html = '';
    foreach ($annonces as $annonce) {
        $projectDir = realpath($this->getParameter('kernel.project_dir'));
        $imagePath = $this->getParameter('image_directory') . '/' . basename($annonce->getImage());
        $file = fopen($imagePath, 'r');
        $image_data = fread($file, filesize($imagePath));
        fclose($file);
        $image = '<img src="data:image/png;base64,' . base64_encode($image_data) . '" />';
        
        $data = [
            'image'        => $image,
            'type'         => $annonce->getType(),
            'description'  => $annonce->getDescription(),
            'nombre de like'   => $annonce->getNbLikes(),
            'nombre de dislikes' => $annonce->getNbDislikes(),
            'user'         => $annonce->getIdUser(),
            'annonce'      => $annonce,
        ];

        $html .= $this->renderView('pdf_generator/index.html.twig', $data);
    }

    $dompdf = new Dompdf();
$dompdf->loadHtml($html);
$dompdf->render();
$output = $dompdf->output();
return new Response(
    $output,
    Response::HTTP_OK,
    ['Content-Type' => 'application/pdf']
);

}

    
}
