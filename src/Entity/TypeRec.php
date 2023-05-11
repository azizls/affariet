<?php



namespace App\Entity;

use App\Repository\TypeRecRepository;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=TypeRecRepository::class)
 */
class TypeRec
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
    private ?string $type_reclamation = null;
    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTypeReclamation(): ?string
    {
        return $this->type_reclamation;
    }

    public function setTypeReclamation(string $type_reclamation): self
    {
        $this->type_reclamation = $type_reclamation;

        return $this;
    }


  
}
