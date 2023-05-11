<?php

namespace App\Entity;

use App\Repository\CategorieRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=CategorieRepository::class)
 */
class Categorie
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private ?int $id_categorie = null;

    /**
     * @ORM\Column(length=255)
     * @Assert\Length(min=4, minMessage="Veuillez entrer au minimum 4 caractères.")
     * @Assert\NotBlank(message="Ce champ ne doit pas être vide.")
     */
    private ?string $nom_categorie = null;

    /**
     * @ORM\OneToMany(targetEntity=Produit::class, mappedBy="categorie", cascade={"persist", "remove"}, orphanRemoval=true)
     */
    private Collection $produits;
    
    // ...



    public function __construct()
    {
        $this->produits = new ArrayCollection();
    }

    public function getIdCategorie(): ?int
    {
        return $this->id_categorie;
    }

    public function getNomCategorie(): ?string
    {
        return $this->nom_categorie;
    }

    public function setNomCategorie(string $nom_categorie): self
    {
        $this->nom_categorie = $nom_categorie;

        return $this;
    }

    /**
     * @return Collection<int, Produit>
     */
    public function getProduits(): Collection
    {
        return $this->produits;
    }

    public function addProduit(Produit $produit): self
    {
        if (!$this->produits->contains($produit)) {
            $this->produits->add($produit);
            $produit->setIdCategorie($this);
        }

        return $this;
    }

    public function removeProduit(Produit $produit): self
    {
        if ($this->produits->removeElement($produit)) {
            // set the owning side to null (unless already changed)
            if ($produit->getIdCategorie() === $this) {
                $produit->setIdCategorie(null);
            }
        }

        return $this;
    }
    public function __toString()
{
    return $this->nom_categorie;
}
}
