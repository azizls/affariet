<?php

namespace App\Form;

use App\Entity\ProduitPriver;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Length;
class ProduitPriverType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        ->add('nom', TextType::class, [
            'constraints' => [
                new NotBlank(),
                new Length([
                    'max' => 100,
                    'maxMessage' => 'Le nom ne doit pas dépasser {{ limit }} caractères.',
                ]),
            ],
        ])
        ->add('description',  TextareaType::class, [
            'constraints' => [
                new NotBlank(),
                new Length([
                    'max' => 250,
                    'maxMessage' => 'La description ne doit pas dépasser {{ limit }} caractères.',
                ]),
            ],
        ])
        
        ->add('image', FileType::class, [
            'label' => 'Image (JPG, PNG or GIF file)',
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
        
        ->add('save',SubmitType::class);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => ProduitPriver::class,
        ]);
    }
}

