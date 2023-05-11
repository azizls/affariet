<?php

namespace App\Entity;

use App\Repository\ProduitRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\ORM\Mapping as ORM;


/**
 * @ORM\Entity(repositoryClass=ProduitRepository::class)
 */
class Produit
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("produits")]
    private ?int $id_produit = null;

    /**
     * @ORM\Column(length=255)
     * @Assert\Length(min=4, minMessage="Veuillez entrer au minimum 4 caractères.")
     * @Assert\NotBlank(message="Ce champ ne doit pas être vide.")
     */
    #[ORM\Column]
    #[Groups("produits")]
    private ?string $nom_produit = null;

    /**
     * @ORM\Column(length=255)
     * @Assert\Length(min=4, minMessage="Veuillez entrer au minimum 4 caractères.")
     * @Assert\NotBlank(message="Ce champ ne doit pas être vide.")
     */
    #[ORM\Column]
    #[Groups("produits")]
    private ?string $description_produit = null;

    /**
     * @ORM\Column(type="float")
     * @Assert\GreaterThanOrEqual(value=0, message="Le prix ne peut pas être négatif.")
     */
    #[ORM\Column]
    #[Groups("produits")]
    private ?float $prix_produit = null;

    /**
     * @ORM\Column(length=255, nullable=true)
     */
    #[ORM\Column]
    #[Groups("produits")]
    private ?string $image_produit = null;

    /**
     * @ORM\ManyToOne(targetEntity=Categorie::class, inversedBy="produits")
     * @ORM\JoinColumn(name="id_categorie", referencedColumnName="id_categorie", nullable=false)
     */
    
    private ?Categorie $id_categorie = null;

   /**
     * @ORM\ManyToOne(targetEntity=User::class)
     * @ORM\JoinColumn(name="id_user", referencedColumnName="id_user")
     */
    
    private ?User $user = null;


    public function getIdProduit(): ?int
    {
        return $this->id_produit;
    }

    public function getNomProduit(): ?string
    {
        return $this->nom_produit;
    }

    public function setNomProduit(string $nom_produit): self
    {
        $this->nom_produit = $nom_produit;

        return $this;
    }

    public function getDescriptionProduit(): ?string
    {
        return $this->description_produit;
    }

    public function setDescriptionProduit(string $description_produit): self
    {
        $this->description_produit = $description_produit;

        return $this;
    }

    public function getPrixProduit(): ?float
    {
        return $this->prix_produit;
    }

    public function setPrixProduit(float $prix_produit): self
    {
        $this->prix_produit = $prix_produit;

        return $this;
    }

    public function getImageProduit(): ?string
    {
        return $this->image_produit;
    }

    public function setImageProduit(string $image_produit): self
    {
        $this->image_produit = $image_produit;

        return $this;
    }

    public function getIdCategorie(): ?Categorie
    {
        return $this->id_categorie;
    }

    public function setIdCategorie(?Categorie $id_categorie): self
    {
        $this->id_categorie = $id_categorie;

        return $this;
    }

    



   
    /**
 * @ORM\OneToMany(targetEntity=Favori::class, mappedBy="produit")
 */
private Collection $favoris;

    public function __construct()
    {
        $this->favoris = new ArrayCollection();
    }


    public function getNombreFavoris(): int
    {
        return $this->favoris->count();
    }



public function setIdUser(User $user): self
{
    $this->user = $user;
    return $this;
}

public function getIdUser(): ?User
{
    return $this->user;
}

/**
 * @return Collection<int, Favori>
 */
public function getFavoris(): Collection
{
    return $this->favoris;
}

public function addFavori(Favori $favori): self
{
    if (!$this->favoris->contains($favori)) {
        $this->favoris->add($favori);
        $favori->setProduit($this);
    }

    return $this;
}

public function removeFavori(Favori $favori): self
{
    if ($this->favoris->removeElement($favori)) {
        // set the owning side to null (unless already changed)
        if ($favori->getProduit() === $this) {
            $favori->setProduit(null);
        }
    }

    return $this;
}


public function isFavoriByUser(?User $user): bool
{
    if (!$user) {
        return false;
    }

    foreach ($this->favoris as $favori) {
        if ($favori->getUser() === $user) {
            return true;
        }
    }

    return false;
}









}
