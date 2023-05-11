<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;
/**
 * @ORM\Entity(repositoryClass=CommandeRepository::class)
 */
class Commande
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("commandes")]
    private ?int $id = null;

    /**
     * @ORM\ManyToOne(targetEntity=Produit::class)
     * @ORM\JoinColumn(name="id_produit", referencedColumnName="id_produit")
     */
    #[ORM\Column]
    #[Groups("commandes")]
    private ?Produit $produit = null;

   /**
     * @ORM\ManyToOne(targetEntity=User::class)
     * @ORM\JoinColumn(name="user", referencedColumnName="id_user")
     */
    #[ORM\Column]
    #[Groups("commandes")]
    private ?User $user = null;

    /**
     * @ORM\Column(type=Types::DATE_MUTABLE)
     */
    #[ORM\Column]
    #[Groups("commandes")]
    private ?\DateTimeInterface $date_commande = null;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    #[ORM\Column]
    #[Groups("commandes")]
    private ?string $chargeId = null;

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

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }

    public function getDateCommande(): ?\DateTimeInterface
    {
        return $this->date_commande;
    }

    public function setDateCommande(\DateTimeInterface $date_commande): self
    {
        $this->date_commande = $date_commande;

        return $this;
    }

    public function getChargeId(): ?string
    {
        return $this->chargeId;
    }

    public function setChargeId(?string $chargeId): self
    {
        $this->chargeId = $chargeId;

        return $this;
    }
}
