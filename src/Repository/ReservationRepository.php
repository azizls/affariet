<?php

// src/Repository/ReservationRepository.php

namespace App\Repository;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use Doctrine\ORM\EntityRepository;

class ReservationRepository extends ServiceEntityRepository
{

    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Evenement::class);
    }

    public function hasUserReservedEvent($eventId, $userId)
    {
        $qb = $this->createQueryBuilder('r')
            ->select('COUNT(r.idRes)')
            ->where('r.idEvent = :eventId')
            ->andWhere('r.idUser1 = :userId')
            ->setParameter('eventId', $eventId)
            ->setParameter('userId', $userId);
    
        $count = $qb->getQuery()->getSingleScalarResult();
    
        return $count > 0;
    }
    


}
