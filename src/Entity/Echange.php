<?php

namespace App\Entity;

use App\Repository\EchangeRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=EchangeRepository::class)
 */
class Echange
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column
     */
    private ?int $id = null;

     /**
     * @ORM\ManyToOne(targetEntity=Produit::class)
     * @ORM\JoinColumn(name="produit_id", referencedColumnName="id_produit")
     */
    private ?Produit $produit = null;

     /**
     * @ORM\ManyToOne(targetEntity=ProduitPriver::class)
     * @ORM\JoinColumn(name="produit_prives_id", referencedColumnName="id")
     */
    private ?ProduitPriver $ProduitPrives = null;

     /**
     * @ORM\ManyToOne(targetEntity=User::class)
     * @ORM\JoinColumn(name="userP", referencedColumnName="id_user")
     */
    
    private ?User $userP = null;

    public function getId(): ?int
    {
        return $this->id;
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

    public function getProduitPrives(): ?ProduitPriver
    {
        return $this->ProduitPrives;
    }

    public function setProduitPrives(?ProduitPriver $ProduitPrives): self
    {
        $this->ProduitPrives = $ProduitPrives;

        return $this;
    }

    public function getUserP(): ?User
    {
        return $this->userP;
    }

    public function setUserP(?User $userP): self
    {
        $this->userP = $userP;

        return $this;
    }
}
