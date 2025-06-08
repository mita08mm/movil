import { Controller, Get, Post, Body, Patch, Param, Delete, UseGuards, Request, HttpCode, HttpStatus } from '@nestjs/common';
import { NotificationsService } from '../services/notifications.service';
import { JwtAuthGuard } from '../../auth/guards/jwt-auth.guard';
import { CreateNotificationDto } from '../dto/create-notification.dto';
import { UpdateNotificationDto } from '../dto/update-notification.dto';

@Controller('notifications')
@UseGuards(JwtAuthGuard)
export class NotificationsController {
  constructor(
    private readonly notificationsService: NotificationsService
  ) {}

  @Post()
  @HttpCode(HttpStatus.CREATED)
  create(@Body() createNotificationDto: CreateNotificationDto, @Request() req) {
    const userId = req.user.sub;
    return this.notificationsService.create(userId, createNotificationDto);
  }

  @Get()
  @HttpCode(HttpStatus.OK)
  findAll(@Request() req) {
    const userId = req.user.sub;
    return this.notificationsService.findByUserId(userId);
  }

  @Get('unread')
  @HttpCode(HttpStatus.OK)
  findUnread(@Request() req) {
    const userId = req.user.sub;
    return this.notificationsService.findUnreadByUserId(userId);
  }

  @Get('count')
  @HttpCode(HttpStatus.OK)
  getUnreadCount(@Request() req) {
    const userId = req.user.sub;
    return this.notificationsService.getUnreadCount(userId);
  }

  @Get(':id')
  @HttpCode(HttpStatus.OK)
  findOne(@Param('id') id: string, @Request() req) {
    const userId = req.user.sub;
    return this.notificationsService.findOne(+id, userId);
  }

  @Patch(':id')
  @HttpCode(HttpStatus.OK)
  update(@Param('id') id: string, @Body() updateNotificationDto: UpdateNotificationDto, @Request() req) {
    const userId = req.user.sub;
    return this.notificationsService.update(+id, userId, updateNotificationDto);
  }

  @Patch(':id/read')
  @HttpCode(HttpStatus.OK)
  markAsRead(@Param('id') id: string, @Request() req) {
    const userId = req.user.sub;
    return this.notificationsService.markAsRead(+id, userId);
  }

  @Patch('read-all')
  @HttpCode(HttpStatus.NO_CONTENT)
  markAllAsRead(@Request() req) {
    const userId = req.user.sub;
    return this.notificationsService.markAllAsRead(userId);
  }

  @Delete(':id')
  @HttpCode(HttpStatus.NO_CONTENT)
  remove(@Param('id') id: string, @Request() req) {
    const userId = req.user.sub;
    return this.notificationsService.remove(+id, userId);
  }
} 