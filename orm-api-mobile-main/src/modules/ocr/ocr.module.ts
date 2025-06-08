import { Module } from '@nestjs/common';
import { MulterModule } from '@nestjs/platform-express';
import { OcrController } from './ocr.controller';
import { OcrService } from './ocr.service';
import { join } from 'path';
import { NotesModule } from '../notes/notes.module';

@Module({
  imports: [
    MulterModule.register({
      dest: join(__dirname, '../../../uploads'),
    }),
    NotesModule,
  ],
  controllers: [OcrController],
  providers: [OcrService],
})
export class OcrModule {} 