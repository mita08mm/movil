import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { NotesService } from './services/notes.service';
import { CategoriesService } from './services/categories.service';
import { NotesController } from './controllers/notes.controller';
import { CategoriesController } from './controllers/categories.controller';
import { Note } from './entities/note.entity';
import { Category } from './entities/category.entity';
import { ConfigModule } from '@nestjs/config';

@Module({
  imports: [
    TypeOrmModule.forFeature([Note, Category]),
    ConfigModule,
  ],
  controllers: [NotesController, CategoriesController],
  providers: [NotesService, CategoriesService],
  exports: [NotesService, CategoriesService],
})
export class NotesModule {}
