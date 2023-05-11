<?php

namespace App\Repository;

use App\Entity\Echange;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Echange>
 *
 * @method Echange|null find($id, $lockMode = null, $lockVersion = null)
 * @method Echange|null findOneBy(array $criteria, array $orderBy = null)
 * @method Echange[]    findAll()
 * @method Echange[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class EchangeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Echange::class);
    }

    public function save(Echange $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Echange $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }


    public function ListeEchangeByUser($user)
    {
        $entityManager = $this->getEntityManager();
        $query = $entityManager->createQuery(
            'SELECT e FROM  App\Entity\Echange e JOIN e.produit p WHERE p.user = :user')
            ->setParameter('user', $user);
        return $query->getResult();
    }
    public function ListeEchangeParNom($nom)
    {
        $entityManager = $this->getEntityManager();
        $query = $entityManager->createQuery(
            'SELECT e FROM  App\Entity\Echange e JOIN e.ProduitPrives p  JOIN p.iduser u WHERE u.nom = :nom')
            ->setParameter('nom', $nom);
        return $query->getResult();
    }
    
    public function countEchange($user) {
        $entityManager = $this->getEntityManager();
        $query = $entityManager
            ->createQuery("SELECT COUNT(e) FROM App\Entity\Echange e JOIN e.produit p WHERE p.user = :user")
            ->setParameter('user', $user);
        return $query->getSingleScalarResult();
    }
    
//    /**
//     * @return Echange[] Returns an array of Echange objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('e.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Echange
//    {
//        return $this->createQueryBuilder('e')
//            ->andWhere('e.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
