import { Controller, Get, Post, Body, Patch, Param, Delete, UseGuards, Request, HttpCode, HttpStatus, Query } from '@nestjs/common';
import { TasksService } from '../services/tasks.service';
import { JwtAuthGuard } from '../../auth/guards/jwt-auth.guard';
import { CreateTaskDto } from '../dto/create-task.dto';
import { UpdateTaskDto } from '../dto/update-task.dto';
import { TaskStatus } from '../task-status.enum';

@Controller('tasks')
@UseGuards(JwtAuthGuard)
export class TasksController {
  constructor(
    private readonly tasksService: TasksService
  ) {}

  @Post()
  @HttpCode(HttpStatus.CREATED)
  async create(@Body() createTaskDto: CreateTaskDto, @Request() req) {
    const userId = req.user.sub;
    return this.tasksService.create(userId, createTaskDto);
  }

  @Get()
  @HttpCode(HttpStatus.OK)
  findAll(@Request() req, @Query('status') status?: TaskStatus) {
    const userId = req.user.sub;
    
    if (status) {
      return this.tasksService.findByUserIdAndStatus(userId, status);
    }
    
    return this.tasksService.findByUserId(userId);
  }

  @Get('overdue')
  @HttpCode(HttpStatus.OK)
  findOverdue(@Request() req) {
    const userId = req.user.sub;
    return this.tasksService.findOverdueTasks(userId);
  }

  @Get('stats')
  @HttpCode(HttpStatus.OK)
  getStats(@Request() req) {
    const userId = req.user.sub;
    return this.tasksService.getStats(userId);
  }

  @Get('stats/today')
  @HttpCode(HttpStatus.OK)
  getTodayStats(@Request() req) {
    const userId = req.user.sub;
    return this.tasksService.getTodayStats(userId);
  }

  @Get('today/pending')
  @HttpCode(HttpStatus.OK)
  getTodayPending(@Request() req) {
    const userId = req.user.sub;
    return this.tasksService.getTodayPending(userId);
  }

  @Get(':id')
  @HttpCode(HttpStatus.OK)
  findOne(@Param('id') id: string, @Request() req) {
    const userId = req.user.sub;
    return this.tasksService.findOne(+id, userId);
  }

  @Patch(':id')
  @HttpCode(HttpStatus.OK)
  update(@Param('id') id: string, @Body() updateTaskDto: UpdateTaskDto, @Request() req) {
    const userId = req.user.sub;
    return this.tasksService.update(+id, userId, updateTaskDto);
  }

  @Delete(':id')
  @HttpCode(HttpStatus.NO_CONTENT)
  remove(@Param('id') id: string, @Request() req) {
    const userId = req.user.sub;
    return this.tasksService.remove(+id, userId);
  }
} 