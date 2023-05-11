<?php

namespace App\Entity;

use DateTimeImmutable;
use DateTimeInterface;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Annonce
 *
 * @ORM\Table(name="annonce", indexes={@ORM\Index(name="id_user_idx", columns={"id_user"})})
 * @ORM\Entity
 */
class Annonce
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_annonce", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idAnnonce;

    /**
     * @var string
     *
     * @ORM\Column(name="type", type="string", length=45, nullable=false)
     */
    private $type;

   /**
 * @var string|null
 *
 * @ORM\Column(name="description", type="string", length=500, nullable=true)
 * @Assert\Regex(
 *     pattern="/\b(fuck|shit)\b/i",
 *     match=false,
 *     message="The description should not contain offensive words."
 * )
 */
private $description;

    /**
     * @var \DateTimeInterface
     *
     * @ORM\Column(name="date_annonce", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $dateAnnonce;
/**
 * @var string|null
 *
 * @ORM\Column(name="image", type="string", length=255, nullable=true)
 */
private $image;

    /**
     * @var int|null
     *
     * @ORM\Column(name="nblikes", type="integer", nullable=true)
     */
    private $nblikes;

    /**
     * @var int|null
     *
     * @ORM\Column(name="nbdislikes", type="integer", nullable=true)
     */
    private $nbdislikes;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="id_user")
     * })
     */
    private $idUser;

   


    public function __construct()
    {
        $this->commentaire = new ArrayCollection();
        $this->dateAnnonce = new DateTimeImmutable();
    }

    public function getIdAnnonce(): ?int
    {
        return $this->idAnnonce;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): self
    {
        $this->type = $type;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getDateAnnonce(): ?DateTimeInterface
    {
        return $this->dateAnnonce;
    }

    public function setDateAnnonce(DateTimeInterface $dateAnnonce): self
    {
        $this->dateAnnonce = $dateAnnonce;

        return $this;
    }

    public function getImage()
    {
        return $this->image;
    }

    public function setImage($image): self
    {
        $this->image = $image;

        return $this;
    }


    public function getNblikes(): ?int
    {
        return $this->nblikes;
    }

    public function setNblikes(?int $nblikes): self
    {
        $this->nblikes = $nblikes;

        return $this;
    }

    public function getNbdislikes(): ?int
    {
        return $this->nbdislikes;
    }

    public function setNbdislikes(?int $nbdislikes): self
    {
        $this->nbdislikes = $nbdislikes;

        return $this;
    }

    public function getIdUser(): ?User
    {
        return $this->idUser;
    }

    public function setIdUser(?User $idUser): self
    {
        $this->idUser = $idUser;

        return $this;
    }


// ...

    /**
     * @ORM\OneToMany(targetEntity="App\Entity\Commentaire", mappedBy="annonce")
     */
    private $commentaire;

   

    /**
     * @return Collection|Commentaire[]
     */
    public function getCommentaire(): Collection
    {
        return $this->commentaire;
    }

    

}