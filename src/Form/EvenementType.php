<?php

namespace App\Form;

use App\Entity\Evenement;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\NotNull;
use Symfony\Component\Validator\Constraints\Range;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\DateTimeType;




class EvenementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('nomEvent', TextType::class, [
            'constraints' => [
                new NotBlank(),
                new Length([
                    'max' => 30,
                    'maxMessage' => 'Le nom de l\'événement ne doit pas dépasser {{ limit }} caractères.'
                ]),
            ],
        ])
        ->add('emplacement', TextType::class, [
            'constraints' => [
                new NotBlank(),
                new Length([
                    'max' => 300,
                    'maxMessage' => 'L\'emplacement ne doit pas dépasser {{ limit }} caractères.'
                ]),
            ],
        ])
        ->add('dateDebut', DateTimeType::class, [
            'date_widget' => 'single_text',
            'constraints' => [new NotNull()],
            'data' => new \DateTime(), // Définit la date de début à aujourd'hui
        ])
        ->add('dateFin', DateTimeType::class, [
            'date_widget' => 'single_text',
            'constraints' => [new NotNull()],
            'data' => new \DateTime(), // Définit la date de début à aujourd'hui

        ])
        ->add('descriptionEvent', TextareaType::class, [
            'constraints' =>  [
                new NotBlank(),
                new Length([
                    'max' => 250,
                    'maxMessage' => 'La descriptionEvent ne doit pas dépasser {{ limit }} caractères.'
                ]),
            ],
        ])
        ->add('prix', IntegerType::class, [
            'constraints' => [new NotNull(), new Range(['min' => 0])],
        ])
        ->add('affiche' , FileType::class, [
            'label' => 'affiche',
            'mapped' => false,
            'required' => false,
            'attr' => [
                'accept' => '.jpg,.jpeg,.png,.gif',
            ],
            'constraints' => [
                new NotBlank([
                    'message' => 'Please upload an image',
                ]),
            ],
        ])
        ->add('nbrMax', IntegerType::class, [
            'required' => false,
            'constraints' => [new Range(['min' => 0])],
        ])
    ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Evenement::class,
            'csrf_protection' => false,
        ]);
    }
}
