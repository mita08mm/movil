import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Note } from '../entities/note.entity';
import { CreateNoteDto } from '../dto/create-note.dto';
import { UpdateNoteDto } from '../dto/update-note.dto';

@Injectable()
export class NotesService {
  constructor(
    @InjectRepository(Note)
    private noteRepository: Repository<Note>,
  ) {}

  async create(createNoteDto: CreateNoteDto, userId: string): Promise<Note> {
    const note = this.noteRepository.create({
      ...createNoteDto,
      userId,
    });
    
    return this.noteRepository.save(note);
  }

  async findAll(userId: string): Promise<Note[]> {
    return this.noteRepository.find({
      where: { userId },
      order: { creationDate: 'DESC' },
      relations: ['category'],
    });
  }

  async findOne(id: number, userId: string): Promise<Note> {
    const note = await this.noteRepository.findOne({
      where: { id, userId },
      relations: ['category'],
    });
    
    if (!note) {
      throw new NotFoundException(`Note with ID ${id} not found`);
    }
    
    return note;
  }

  async update(id: number, updateNoteDto: UpdateNoteDto, userId: string): Promise<Note> {
    const note = await this.findOne(id, userId);
    
    const updatedNote = this.noteRepository.merge(note, updateNoteDto);
    return this.noteRepository.save(updatedNote);
  }

  async remove(id: number, userId: string): Promise<void> {
    const note = await this.findOne(id, userId);
    
    await this.noteRepository.remove(note);
  }

  async getCount(userId: number) {
    const count = await this.noteRepository.count({ where: { userId: userId.toString() } });
    return { count };
  }
}
