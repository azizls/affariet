<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
/**
 * @ORM\Entity
 * @ORM\Table(name="commentaire",
 *     indexes={
 *         @ORM\Index(name="id_annonce_idx", columns={"id_annonce"}),
 *         @ORM\Index(name="id_user1_idx", columns={"id_user1"})
 *     },
 *     uniqueConstraints={
 *         @ORM\UniqueConstraint(name="commentaire_pk", columns={"id_annonce", "id_commentaire"})
 *     }
 * )
 */
class Commentaire
{
    /**
     * @ORM\Column(name="id_annonce", type="integer")
     */
    private $idAnnonce;

    /**
     * @ORM\Id()
     * @ORM\Column(name="id_commentaire", type="integer")
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $idCommentaire;

    /**
 * @var string|null
 *
 * @ORM\Column(name="comment", type="string", length=500, nullable=true)
 * @Assert\Regex(
 *     pattern="/\b(fuck|shit)\b/i",
 *     match=false,
 *     message="The comment should not contain offensive words."
 * )
 */
private $comment;


    /**
     * @var \Annonce
     *
     * @ORM\ManyToOne(targetEntity="App\Entity\Annonce", inversedBy="commentaires")
     * @ORM\JoinColumn(name="id_annonce", referencedColumnName="id_annonce", nullable=false)
     */
    private $annonce;

   /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user1", referencedColumnName="id_user")
     * })
     */
    private $idUser1;

    // ...



    public function getIdCommentaire(): ?int
    {
        return $this->idCommentaire;
    }

    public function getComment(): ?string
    {
        return $this->comment;
    }

    public function setComment(?string $comment): self
    {
        $this->comment = $comment;

        return $this;
    }

    public function getAnnonce(): ?Annonce
    {
        return $this->annonce;
    }

    public function setAnnonce(?Annonce $annonce): self
    {
        $this->annonce = $annonce;

        return $this;
    }

    public function getIdUser1(): ?User
    {
        return $this->idUser1;
    }

    public function setIdUser1(?User $idUser1): self
    {
        $this->idUser1 = $idUser1;

        return $this;
    }

    public function __toString()
    {
        return $this->comment;
    }

    public function getIdAnnonce(): ?int
    {
        return $this->idAnnonce;
    }

    public function setIdAnnonce(?int $idAnnonce): self
    {
        $this->idAnnonce = $idAnnonce;

        return $this;
    }
}