<?php

namespace App\Form;

use App\Entity\Reclamation;
use App\Entity\TypeRec;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;

class ReclamationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('date_reclamation', DateTimeType::class, [
                'data' => new \DateTime(),
                'disabled' => true,
            ])
            ->add('description', TextareaType::class, [
                'attr' => ['placeholder' => 'Contenu de la reclamation'],
                'constraints' => [
                    new Length([
                        'min' => 15,
                        'max' => 500,
                        'minMessage' => 'Le contenu doit contenir au moins {{ limit }} caractères (sans les espaces).',
                        'maxMessage' => 'Le contenu ne doit pas dépasser {{ limit }} caractères (sans les espaces).'
                    ])
                ]
            ])
            ->add('etat_reclamation', ChoiceType::class, [
                'choices' => [
                    'Traité' => 'Traité',
                    'Non traité' => 'Non traité',
                    'En cours' => 'En cours',
                ],
                'placeholder' => 'Sélectionner l\'état de la réclamation',
            ])
            ->add('avis')
            ->add('type_reclamation', EntityType::class, [
                'class' => TypeRec::class,
                'choice_label' => 'type_reclamation',
            ])
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Reclamation::class,
        ]);
    }
}