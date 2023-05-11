<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Context\ExecutionContextInterface;
use Symfony\Component\Serializer\Annotation\Groups;



/**
 * Evenement
 *
 * @ORM\Table(name="evenement", indexes={@ORM\Index(name="id_user_idx", columns={"id_user"})})
 * @ORM\Entity
 */
class Evenement
{
    #[Groups("events")]
    /**
     * @var int
     *
     * @ORM\Column(name="id_event", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * 
     */
    private $idEvent;

    #[Groups("events")]
    /**
     * @var string
     *
     * @ORM\Column(name="nom_event", type="string", length=45, nullable=false)
     * 
     */
    private $nomEvent;
    
    #[Groups("events")]
    /**
     * @var string
     *
     * @ORM\Column(name="emplacement", type="string", length=45, nullable=false)
     * 
     */
    private $emplacement;
    
   
    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_debut", type="date", nullable=false)
     * 
     */
    private $dateDebut;
 
    
    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_fin", type="date", nullable=false)
     * 
     */
    private $dateFin;
    
    #[Groups("events")]

    /**
     * @var string
     *
     * @ORM\Column(name="description_event", type="string", length=45, nullable=false)
     *
     */
    private $descriptionEvent;

    #[Groups("events")]
    /**
     * @var int
     *
     * @ORM\Column(name="prix", type="integer", nullable=false)
     * 
     */
    private $prix;

    #[Groups("events")]
    /**
     * @var string|null
     *
     * @ORM\Column(name="affiche", type="string", length=45, nullable=true)
     * 
     */
    private $affiche;

    #[Groups("events")]
    /**
     * @var int|null
     *
     * @ORM\Column(name="nbr_max", type="integer", nullable=true)
     * 
     */
    private $nbrMax;

    /**
     * @var int
     *
     * @ORM\Column(name="NbrView", type="integer", nullable=false)
     * 
     */
    private $NbrView;

    /**
     * @var string
     *
     * @ORM\Column(name="random_color", length=45, nullable=false)
     * 
     */
    private $randomColor;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="id_user")
     *
     * })
     */
    private $idUser;

    public function getIdEvent(): ?int
    {
        return $this->idEvent;
    }

    public function getNomEvent(): ?string
    {
        return $this->nomEvent;
    }

    public function setNomEvent(string $nomEvent): self
    {
        $this->nomEvent = $nomEvent;

        return $this;
    }

    public function getEmplacement(): ?string
    {
        return $this->emplacement;
    }

    public function setEmplacement(string $emplacement): self
    {
        $this->emplacement = $emplacement;

        return $this;
    }

    public function getDateDebut(): ?\DateTimeInterface
    {
        return $this->dateDebut;
    }

    public function setDateDebut(\DateTimeInterface $dateDebut): self
    {
        $this->dateDebut = $dateDebut;

        return $this;
    }

    public function getDateFin(): ?\DateTimeInterface
    {
        return $this->dateFin;
    }

    public function setDateFin(\DateTimeInterface $dateFin): self
    {
        $this->dateFin = $dateFin;

        return $this;
    }

    public function getDescriptionEvent(): ?string
    {
        return $this->descriptionEvent;
    }

    public function setDescriptionEvent(string $descriptionEvent): self
    {
        $this->descriptionEvent = $descriptionEvent;

        return $this;
    }

    public function getPrix(): ?int
    {
        return $this->prix;
    }

    public function setPrix(int $prix): self
    {
        $this->prix = $prix;

        return $this;
    }

    public function getAffiche(): ?string
    {
        return $this->affiche;
    }

    public function setAffiche(?string $affiche): self
    {
        $this->affiche = $affiche;

        return $this;
    }

    public function getNbrMax(): ?int
    {
        return $this->nbrMax;
    }

    public function setNbrMax(?int $nbrMax): self
    {
        $this->nbrMax = $nbrMax;

        return $this;
    }
    public function getNbrView(): ?int
    {
        return $this->NbrView;
    }

    public function setNbrView(int $NbrView): self
    {
        $this->NbrView = $NbrView;

        return $this;
    }
    public function getRandom(): ?string
    {
        return $this->randomColor;
    }

    public function setRandom(string $randomColor): self
    {
        $this->randomColor = $randomColor;

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
/**
     * @Assert\Callback
     */
    public function validateDates(ExecutionContextInterface $context, $payload)
    {
        if ($this->getDateFin() < $this->getDateDebut()) {
            $context->buildViolation('La date de début doit être antérieure à la date de fin')
                ->atPath('dateDebut')
                ->addViolation();
        }
    }

}