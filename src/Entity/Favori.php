<?php

namespace App\Entity;

use App\Repository\FavoriRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=FavoriRepository::class)
 */
class Favori
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column
     */
    private ?int $id = null;

    /**
     * @ORM\ManyToOne(inversedBy="favoris")
     * @ORM\JoinColumn(name="id_user", referencedColumnName="id_user")
     */
    private ?User $user = null;

    /**
     * @ORM\ManyToOne(inversedBy="favoris")
     * @ORM\JoinColumn(name="id_produit", referencedColumnName="id_produit")
     */
    private ?Produit $produit = null;



    public function getId(): ?int
    {
        return $this->id;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }

    public function getProduit(): ?Produit
    {
        return $this->produit;
    }

    public function setProduit(?Produit $produit): self
    {
        $this->produit = $produit;

        return $this;
    }
}
