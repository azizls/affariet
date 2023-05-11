<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use App\Repository\ProduitPriverRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=ProduitPriverRepository::class)
 */
class ProduitPriver
{
   /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private ?int $id = null;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private ?string $nom = null;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private ?string $description = null;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private ?string $etat = null;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    private ?string $image = null;

    /**
     * @ORM\ManyToOne(targetEntity=User::class)
     * @ORM\JoinColumn(name="iduser_id", referencedColumnName="id_user")
     */
    private $iduser;
    

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getEtat(): ?string
    {
        return $this->etat;
    }

    public function setEtat(string $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(string $image): self
    {
        $this->image = $image;

        return $this;
    }

    public function getIduser(): ?User
    {
        return $this->iduser;
    }

    public function setIduser(?User $iduser): self
    {
        $this->iduser = $iduser;

        return $this;
    }

    /**
     * @return Collection<int, Echange>
     */
    public function getEchangesProduitPrives(): Collection
    {
        return $this->echangesProduitPrives;
    }

    public function addEchangesProduitPrife(Echange $echangesProduitPrife): self
    {
        if (!$this->echangesProduitPrives->contains($echangesProduitPrife)) {
            $this->echangesProduitPrives->add($echangesProduitPrife);
            $echangesProduitPrife->setProduitPrives($this);
        }

        return $this;
    }

    public function removeEchangesProduitPrife(Echange $echangesProduitPrife): self
    {
        if ($this->echangesProduitPrives->removeElement($echangesProduitPrife)) {
            // set the owning side to null (unless already changed)
            if ($echangesProduitPrife->getProduitPrives() === $this) {
                $echangesProduitPrife->setProduitPrives(null);
            }
        }

        return $this;
    }
}
