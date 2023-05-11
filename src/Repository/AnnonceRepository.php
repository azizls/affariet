<?php
// src/Repository/AnnonceRepository.php

namespace App\Repository;

use App\Entity\Annonce;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Annonce>
 *
 * @method Annonce|null find($id, $lockMode = null, $lockVersion = null)
 * @method Annonce|null findOneBy(array $criteria, array $orderBy = null)
 * @method Annonce[]    findAll()
 * @method Annonce[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class AnnonceRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Annonce::class);
    }

    public function save(Annonce $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Annonce $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

//    /**
//     * @return Annonce[] Returns an array of Annonce objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('a')
//            ->andWhere('a.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('a.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Annonce
//    {
//        return $this->createQueryBuilder('a')
//            ->andWhere('a.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }


public function findByCommentaireId(int $idCommentaire): ?Annonce
{
    return $this->createQueryBuilder('a')
        ->join('a.commentaire', 'c')
        ->where('c.idCommentaire = :idCommentaire')
        ->setParameter('idCommentaire', $idCommentaire)
        ->getQuery()
        ->getOneOrNullResult();
}

public function findAnnoncesByUser(User $user)
{
    return $this->createQueryBuilder('a')
        ->andWhere('a.idUser = :user')
        ->setParameter('user', $user)
        ->getQuery()
        ->getResult();
}

public function findByCriteria($type, $description, $idUser)
{
    $qb = $this->createQueryBuilder('a');

    if ($type) {
        $qb->andWhere('a.type = :type')
            ->setParameter('type', $type);
    }

    if ($description) {
        $qb->andWhere('a.description LIKE :description')
            ->setParameter('description', '%' . $description . '%');
    }

    if ($idUser) {
        $qb->join('a.idUser', 'u')
            ->andWhere('u.id = :idUser')
            ->setParameter('idUser', $idUser->getId());
    }

    return $qb->getQuery()->getResult();
}




public function findByMultipleCriteria($type, $user, $description)
{
    $qb = $this->createQueryBuilder('a')
        ->andWhere('a.type LIKE :type')
        ->andWhere('a.idUser LIKE :user')
        ->andWhere('a.description LIKE :description')
        ->setParameter('type', '%'.$type.'%')
        ->setParameter('user', '%'.$user.'%')
        ->setParameter('description', '%'.$description.'%');

    return $qb->getQuery()->getResult();
}



public function rechercheMulticritere(string $search = null, string $type = null)
{
    $query = $this->createQueryBuilder('a');

    if ($search) {
        $query->andWhere('a.description LIKE :search')
            ->setParameter('search', '%'.$search.'%');
    }

    if ($type) {
        $query->andWhere('a.type = :type')
            ->setParameter('type', $type);
    }

    return $query->getQuery()->getResult();
}
}
