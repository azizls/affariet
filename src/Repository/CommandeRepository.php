<?php

namespace App\Repository;

use App\Entity\Commande;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Commande>
 *
 * @method Commande|null find($id, $lockMode = null, $lockVersion = null)
 * @method Commande|null findOneBy(array $criteria, array $orderBy = null)
 * @method Commande[]    findAll()
 * @method Commande[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class CommandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commande::class);
    }

    public function save(Commande $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Commande $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }




    public function countProduitByUser($produit, $user) {
        $entityManager = $this->getEntityManager();
        $query = $entityManager
            ->createQuery("SELECT COUNT(c) FROM App\Entity\Commande c WHERE c.user = :user AND c.produit = :produit")
            ->setParameter('user', $user)
            ->setParameter('produit', $produit);
        return $query->getSingleScalarResult();
    }
    
    public function stat()
    {
    $entityManager = $this->getDoctrine()->getManager();
$query = $entityManager->createQuery(
    'SELECT COUNT(c) as count, MONTH(c.date_commande) as month, YEAR(c.date_commande) as year
     FROM App\Entity\Commande c
     WHERE YEAR(c.date_commande) = :year
     GROUP BY year, month'
)->setParameter('year', date('Y'));

return $query->getResult();
}
    
    public function findCommandesByUser($user)
    {
        $entityManager = $this->getEntityManager();
        $query = $entityManager->createQuery(
            'SELECT c FROM App\Entity\Commande c WHERE c.user = :user'
        )->setParameter('user', $user);
    
        return $query->getResult();
    }


//    /**
//     * @return Commande[] Returns an array of Commande objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('c')
//            ->andWhere('c.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('c.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Commande
//    {
//        return $this->createQueryBuilder('c')
//            ->andWhere('c.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
