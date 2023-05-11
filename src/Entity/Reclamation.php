<?php



namespace App\Entity;

use App\Repository\ReclamationRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=ReclamationRepository::class)
 */
class Reclamation
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups("reclamation")
     */
    private ?int $id = null;

    /**
     * @ORM\Column(type="datetime", nullable=true)
     * @Groups("reclamation")
     */
    private ?\DateTimeInterface $date_reclamation = null;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Groups("reclamation")
     */
    private ?string $description = null;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Groups("reclamation")
     */
    private ?string $etat_reclamation = null;

    /**
     * @ORM\Column(type="integer", nullable=true)
     * @Groups("reclamation")
     */
    private ?int $avis = null;

    /**
     * @ORM\ManyToOne(targetEntity=TypeRec::class)
     * @ORM\JoinColumn(name="type_reclamation", referencedColumnName="id")
     * @Groups("reclamation")
     */
    private ?TypeRec $type_reclamation = null;

    




    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDateReclamation(): ?\DateTimeInterface
    {
        return $this->date_reclamation;
    }

    public function setDateReclamation(?\DateTimeInterface $date_reclamation): self
    {
        $this->date_reclamation = $date_reclamation;

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

    public function getEtatReclamation(): ?string
    {
        return $this->etat_reclamation;
    }

    public function setEtatReclamation(?string $etat_reclamation): self
    {
        $this->etat_reclamation = $etat_reclamation;

        return $this;
    }

    public function getAvis(): ?int
    {
        return $this->avis;
    }

    public function setAvis(?int $avis): self
    {
        $this->avis = $avis;

        return $this;
    }
    public function getTypeReclamation(): ?TypeRec
    {
        return $this->type_reclamation;
    }
    
    public function setTypeReclamation(?TypeRec $type_reclamation): self
    {
        $this->type_reclamation = $type_reclamation;
    
        return $this;
    }
    


}
