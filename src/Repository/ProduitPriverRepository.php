<?php

namespace App\Repository;

use App\Entity\ProduitPriver;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<ProduitPriver>
 *
 * @method ProduitPriver|null find($id, $lockMode = null, $lockVersion = null)
 * @method ProduitPriver|null findOneBy(array $criteria, array $orderBy = null)
 * @method ProduitPriver[]    findAll()
 * @method ProduitPriver[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ProduitPriverRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, ProduitPriver::class);
    }

    public function save(ProduitPriver $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(ProduitPriver $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
    public function findProduitPrivesByUser($user)
    {
        $entityManager = $this->getEntityManager();
        $query = $entityManager->createQuery(
            'SELECT p FROM App\Entity\ProduitPriver p WHERE p.iduser = :user'
        )->setParameter('user', $user);
    
        return $query->getResult();
    }
    public function findProduitPrivesByUserAndDispo($user)
{
    $entityManager = $this->getEntityManager();
    $query = $entityManager->createQuery(
        'SELECT p FROM App\Entity\ProduitPriver p WHERE p.iduser = :user AND p.etat = :status'
    )
    ->setParameter('user', $user)
    ->setParameter('status', 'disponible');

    return $query->getResult();
}


//    /**
//     * @return ProduitPriver[] Returns an array of ProduitPriver objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('p.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?ProduitPriver
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
