<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230425123015 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE user DROP FOREIGN KEY id_role');
        $this->addSql('ALTER TABLE user ADD is_enabled TINYINT(1) NOT NULL, CHANGE reset_token reset_token VARCHAR(180) NOT NULL');
        $this->addSql('ALTER TABLE user ADD CONSTRAINT FK_8D93D649DC499668 FOREIGN KEY (id_role) REFERENCES role (id_role)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE user DROP FOREIGN KEY FK_8D93D649DC499668');
        $this->addSql('ALTER TABLE user DROP is_enabled, CHANGE reset_token reset_token VARCHAR(250) DEFAULT NULL');
        $this->addSql('ALTER TABLE user ADD CONSTRAINT id_role FOREIGN KEY (id_role) REFERENCES role (id_role) ON UPDATE CASCADE ON DELETE CASCADE');
    }
}
