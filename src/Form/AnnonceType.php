<?php

namespace App\Form;

use App\Entity\Annonce;
use App\Entity\User;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Validator\Constraints\File;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;

class AnnonceType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('type', ChoiceType::class, [
            'label' => 'Type',
            'choices' => [
                'Publicité' => 'Publicité',
                'Annonce commerciale' => 'Annonce commerciale',
                'Avis' => 'Avis',
            ],
            'required' => true,
        ])
        
        ->add('description', TextareaType::class, [
            'required' => true,
        ])
            ->add('image', FileType::class, [
                'label' => 'Image (JPG, PNG or GIF file)',
                'required' => false,
                'constraints' => [
                    new File([
                        'maxSize' => '5000k',
                        'mimeTypes' => [
                            'image/jpeg',
                            'image/png',
                            'image/gif',
                        ],
                        'mimeTypesMessage' => 'Please upload a valid image (JPG, PNG or GIF)',
                    ])
                ],
            ])
           
           
            ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Annonce::class,
        ]);
    }
}
