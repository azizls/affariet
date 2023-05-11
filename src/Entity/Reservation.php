<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use App\Repository\ReservationRepository;



/**
 * Reservation
*@ORM\Entity(repositoryClass="App\Repository\ReservationRepository")

 * @ORM\Table(name="reservation", indexes={@ORM\Index(name="id_event_idx", columns={"id_event"}), @ORM\Index(name="id_user_idx", columns={"id_user1"})})
 * ** @UniqueEntity(fields={"idEvent", "idUser1"}, message="Vous avez déjà réservé cet événement.")

 * @ORM\Entity
 */
class Reservation
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_res", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idRes;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_res", type="date", nullable=false)
     */
    private $dateRes;

    /**
     * @var int|null
     *
     * @ORM\Column(name="nbr_de_Res", type="integer", nullable=true)
     */
    private $nbrDeRes;

    /**
     * @var \Evenement
     *
     * @ORM\ManyToOne(targetEntity="Evenement")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_event", referencedColumnName="id_event")
     * })
     */
    private $idEvent;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user1", referencedColumnName="id_user")
     * })
     */
    private $idUser1;

    public function getIdRes(): ?int
    {
        return $this->idRes;
    }

    public function getDateRes(): ?\DateTimeInterface
    {
        return $this->dateRes;
    }

    public function setDateRes(\DateTimeInterface $dateRes): self
    {
        $this->dateRes = $dateRes;

        return $this;
    }

    public function getNbrDeRes(): ?int
    {
        return $this->nbrDeRes;
    }

    public function setNbrDeRes(?int $nbrDeRes): self
    {
        $this->nbrDeRes = $nbrDeRes;

        return $this;
    }

    public function getIdEvent(): ?Evenement
    {
        return $this->idEvent;
    }

    public function setIdEvent(?Evenement $idEvent): self
    {
        $this->idEvent = $idEvent;

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


}
