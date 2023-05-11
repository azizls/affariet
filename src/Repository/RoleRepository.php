<?php


namespace App\Repository;

use App\Entity\Role;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class RoleRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Role::class);
    }

    public function getRoleTypeById(int $idRole): ?string
    {
        $qb = $this->createQueryBuilder('r')
            ->select('r.type')
            ->where('r.idRole = :idRole')
            ->setParameter('idRole', $idRole);

        $query = $qb->getQuery();
        $result = $query->getOneOrNullResult();

        return $result['type'] ?? null;
    }
}
