<?php

namespace App\Entity;
use App\Repository\UserRepository ;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\HttpFoundation\File\File;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * User
 *
 * @ORM\Table(name="user", uniqueConstraints={@ORM\UniqueConstraint(name="email_UNIQUE", columns={"email"})}, indexes={@ORM\Index(name="id_role_idx", columns={"id_role"})})
 * @ORM\Entity
 */
class User implements UserInterface
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_user", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     * 
     */
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    #[Groups("users")]
    private $idUser;

    /**
     * @var string|null
     *
     * @ORM\Column(name="nom", type="string", length=45, nullable=true)
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $nom;

    /**
     * @var string|null
     *
     * @ORM\Column(name="prenom", type="string", length=45, nullable=true)
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]


    private $prenom;

    /**
 * @var int|null
 *
 * @ORM\Column(name="age", type="integer", nullable=true)
 * @Assert\Range(min=10, max=100, notInRangeMessage="Age should be between {{ min }} and {{ max }}")
 * @Groups({"post:read"})
 */
#[ORM\Column]
#[Groups("users")]

    private $age;

    /**
     * @var string
     * @Assert\Email()
     * @ORM\Column(name="email", type="string", length=45, nullable=false)
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $email;

    /**
     * @var string
     * @Assert\Length(
     * min = 8,
     * minMessage = "Your password must be at least {{ limit }} characters long",
     * 
     * )
     * @Assert\Regex(
     * pattern="/^(?=.*[A-Z])(?=.*\d).+$/",
     * match=true,
     * message="Your password must contain at least one uppercase letter and one number"
     * )
     * @ORM\Column(name="mdp", type="string", length=150, nullable=false)
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $mdp;

    /**
     * @var string|null
     * 
     * @ORM\Column(name="image", type="string", length=500, nullable=true)
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $image;

    /**
     * @var int|null
     * @Assert\Regex(
     *pattern="/^[2|5|9][0-9]{7}$/",
     *match=true,
     *message="Your phone number must start with 2, 5 or 9 and be 8 digits long"
     * )
     * @ORM\Column(name="numero", type="integer", nullable=true)
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $numero;

    /**
     * @var string|null
     *
     * @ORM\Column(name="adresse", type="string", length=45, nullable=true)
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $adresse;

    /**
     * @var string|null
     *
     * @ORM\Column(name="code_confirmation", type="string", length=45, nullable=true)
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $codeConfirmation;

    /**
     * @var \Role
     *
     * @ORM\ManyToOne(targetEntity="Role")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_role", referencedColumnName="id_role")
     * })
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $idRole;

    /**
     * @ORM\Column(type="string", length=180, )
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $reset_token;
   
    /**
     * @ORM\Column(type="string", length=250, nullable=true)
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $confirmation_token;
    
    /**
     * @ORM\Column(type="integer")
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $is_enabled=0;

    private $imageFile;

    /**
     * 
     * @Groups({"post:read"})
     */
    #[ORM\Column]
    #[Groups("users")]

    private $imageFilename;

    #[ORM\Column(type: Types::ARRAY)]
    private array $picture_url = [];


    public function getIdUser(): ?int
    {
        return $this->idUser;
    }

    public function getNom(): ?string
    {
        return $this->nom;
    }

    public function setNom(?string $nom): self
    {
        $this->nom = $nom;

        return $this;
    }

    public function getPrenom(): ?string
    {
        return $this->prenom;
    }

    public function setPrenom(?string $prenom): self
    {
        $this->prenom = $prenom;

        return $this;
    }

    public function getAge(): ?int
    {
        return $this->age;
    }

    public function setAge(?int $age): self
    {
        $this->age = $age;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getMdp(): ?string
    {
        return $this->mdp;
    }

    public function setMdp(string $mdp): self
    {
        $this->mdp = $mdp;

        return $this;
    }

    public function getImage(): ?string
    {
        return $this->image;
    }

    public function setImage(?string $image): self
    {
        $this->image = $image;

        return $this;
    }

    public function getNumero(): ?int
    {
        return $this->numero;
    }

    public function setNumero(?int $numero): self
    {
        $this->numero = $numero;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(?string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getCodeConfirmation(): ?string
    {
        return $this->codeConfirmation;
    }

    public function setCodeConfirmation(?string $codeConfirmation): self
    {
        $this->codeConfirmation = $codeConfirmation;

        return $this;
    }

    public function getIdRole(): ?Role
    {
        return $this->idRole;
    }

    public function setIdRole(?Role $idRole): self
    {
        $this->idRole = $idRole;

        return $this;
    }
    



    public function getUsername(): string
    {
        return (string) $this->email;
    }

    public function getPassword(): string
    {
        return (string) $this->mdp;
    }

    public function getRoles(): array
    {
        return [$this->idRole->getIdRole()];
    }

    public function getSalt(): ?string
    {
        // not needed when using bcrypt or argon2i
        return null;
    }

    public function eraseCredentials()
    {
        // not needed when using bcrypt or argon2i
    }


    public function getImageFilename(): ?string
    {
        return $this->image ? basename($this->image) : null;
    }

    public function setImageFile(File $image = null): void
    {
        $this->imageFile = $image;
    }

    public function getImageFile(): ?File
    {
        return $this->imageFile;
    }


    /**
     * @return mixed
     */
    public function getResetToken()
    {
        return $this->reset_token;
    }

    /**
     * @param mixed $reset_token
     */
    public function setResetToken($reset_token): void
    {
        $this->reset_token = $reset_token;
    }

    public function getConfirmationToken(): ?string
    {
        return $this->confirmation_token;
    }

    public function setConfirmationToken(string $token): self
    {
        $this->confirmation_token = $token;

        return $this;
    }
   
    public function getIsEnabled(): int
    {
        return $this->is_enabled;
    }
    
    public function setIsEnabled(int $is_enabled): self
    {
        $this->is_enabled = $is_enabled;
    
        return $this;
    }
    
    public function getPictureUrl(): array
    {
        return $this->picture_url;
    }

    public function setPictureUrl(array $picture_url): self
    {
        $this->picture_url = $picture_url;

        return $this;
    }
}