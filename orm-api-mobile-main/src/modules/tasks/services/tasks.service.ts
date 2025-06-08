import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository, LessThanOrEqual, Between } from 'typeorm';
import { Task } from '../entities/task.entity';
import { TaskStatus } from '../task-status.enum';
import { CreateTaskDto } from '../dto/create-task.dto';
import { UpdateTaskDto } from '../dto/update-task.dto';

@Injectable()
export class TasksService {
  constructor(
    @InjectRepository(Task)
    private taskRepository: Repository<Task>,
  ) {}

  async findAll(): Promise<Task[]> {
    return this.taskRepository.find({
      order: { creationDate: 'DESC' }
    });
  }

  async findByUserId(userId: number): Promise<Task[]> {
    return this.taskRepository.find({
      where: { userId },
      order: { creationDate: 'DESC' }
    });
  }

  async findByUserIdAndStatus(userId: number, status: TaskStatus): Promise<Task[]> {
    return this.taskRepository.find({
      where: { userId, status },
      order: { creationDate: 'DESC' }
    });
  }

  async findOverdueTasks(userId: number): Promise<Task[]> {
    const today = new Date();
    
    return this.taskRepository.find({
      where: {
        userId,
        status: TaskStatus.PENDING,
        dueDate: LessThanOrEqual(today)
      },
      order: { dueDate: 'ASC' }
    });
  }

  async findTasksDueInRange(userId: number, startDate: Date, endDate: Date): Promise<Task[]> {
    return this.taskRepository.find({
      where: {
        userId,
        status: TaskStatus.PENDING,
        dueDate: Between(startDate, endDate)
      },
      order: { dueDate: 'ASC' }
    });
  }

  async findOne(id: number, userId: number): Promise<Task> {
    const task = await this.taskRepository.findOne({ 
      where: { id, userId } 
    });
    
    if (!task) {
      throw new NotFoundException(`Task with ID ${id} not found`);
    }
    
    return task;
  }

  async create(userId: number, createTaskDto: CreateTaskDto): Promise<Task> {
    const task = this.taskRepository.create({
      ...createTaskDto,
      userId
    });
    
    return this.taskRepository.save(task);
  }

  async update(id: number, userId: number, updateTaskDto: UpdateTaskDto): Promise<Task> {
    const task = await this.findOne(id, userId);
    
    Object.assign(task, updateTaskDto);
    
    return this.taskRepository.save(task);
  }

  async remove(id: number, userId: number): Promise<void> {
    const task = await this.findOne(id, userId);
    await this.taskRepository.remove(task);
  }

  async getStats(userId: number): Promise<{ total: number; completed: number; pending: number; archived: number }> {
    const all = await this.findByUserId(userId);
    const completed = all.filter(task => task.status === TaskStatus.COMPLETED).length;
    const pending = all.filter(task => task.status === TaskStatus.PENDING).length;
    const archived = all.filter(task => task.status === TaskStatus.ARCHIVE).length;
    
    return {
      total: all.length,
      completed,
      pending,
      archived
    };
  }

  async getTodayStats(userId: number) {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const tomorrow = new Date(today);
    tomorrow.setDate(today.getDate() + 1);

    const tasks = await this.taskRepository.find({
      where: {
        userId,
        dueDate: Between(today, tomorrow)
      }
    });

    const completed = tasks.filter(t => t.status === TaskStatus.COMPLETED).length;
    const pending = tasks.filter(t => t.status === TaskStatus.PENDING).length;

    return {
      total: tasks.length,
      completed,
      pending
    };
  }

  async getTodayPending(userId: number) {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const tomorrow = new Date(today);
    tomorrow.setDate(today.getDate() + 1);

    return this.taskRepository.find({
      where: {
        userId,
        status: TaskStatus.PENDING,
        dueDate: Between(today, tomorrow)
      },
      order: { dueDate: 'ASC' }
    });
  }
} 