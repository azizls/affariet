<?php

namespace App\Repository;

use App\Entity\Produit;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Produit>
 *
 * @method Produit|null find($id, $lockMode = null, $lockVersion = null)
 * @method Produit|null findOneBy(array $criteria, array $orderBy = null)
 * @method Produit[]    findAll()
 * @method Produit[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ProduitRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Produit::class);
    }

    public function save(Produit $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Produit $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
    public function findProduitByUser($user)
    {
        $entityManager = $this->getEntityManager();
        $query = $entityManager->createQuery(
            'SELECT p FROM App\Entity\Produit p WHERE p.user = :user'
        )->setParameter('user', $user);
    
        return $query->getResult();
    }
    public function findFavoriByUser($user)
    {
        $entityManager = $this->getEntityManager();
        $query = $entityManager->createQuery(
            'SELECT p FROM App\Entity\Favori f WHERE f.user = :user'
        )->setParameter('user', $user);
    
        return $query->getResult();
    }

    public function searchProduits(string $query): array
{
    $qb = $this->createQueryBuilder('p');
    $qb->where($qb->expr()->like('p.nom_produit', ':query'))
        ->setParameter('query', '%'.$query.'%')
        ->orderBy('p.nom_produit', 'ASC');

    return $qb->getQuery()->getResult();
}
public function getCategoryStatistics(): array
{
    $qb = $this->createQueryBuilder('p')
        ->select('c.nom_categorie', 'COUNT(p) as count')
        ->join('p.id_categorie', 'c')
        ->groupBy('c.nom_categorie')
        ->orderBy('count', 'DESC');

    return $qb->getQuery()->getResult();
}
public function getFavoriteProductsCount()
    {
        $qb = $this->createQueryBuilder('p');
        $qb->leftJoin('p.favoris', 'f')
           ->select('p.nom_produit as label, COUNT(f.id) as data')
           ->groupBy('p.nom_produit');
        return $qb->getQuery()->getResult();
    }
    public function findAllWithNombreFavoris(): array
{
    $qb = $this->createQueryBuilder('p')
        ->leftJoin('p.favoris', 'f')
        ->select('p', 'COUNT(f) AS nb_favoris')
        ->groupBy('p')
        ->orderBy('nb_favoris', 'DESC');

    return $qb->getQuery()->getResult();
}

    


//    /**
//     * @return Produit[] Returns an array of Produit objects
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

//    public function findOneBySomeField($value): ?Produit
//    {
//        return $this->createQueryBuilder('p')
//            ->andWhere('p.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
