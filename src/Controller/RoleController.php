<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Routing\Annotation\Route;
use App\Entity\Role;

class RoleController extends AbstractController
{
    /**
     * @Route("/roles", name="roles")
     */
    public function index()
    {
        $roles = $this->getDoctrine()
            ->getRepository(Role::class)
            ->findAll();

        return $this->render('roles/index.html.twig', [
            'roles' => $roles,
        ]);
    }
}
