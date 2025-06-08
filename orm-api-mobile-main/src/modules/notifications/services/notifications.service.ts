import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Notification } from '../entities/notification.entity';
import { CreateNotificationDto } from '../dto/create-notification.dto';
import { UpdateNotificationDto } from '../dto/update-notification.dto';

@Injectable()
export class NotificationsService {
  constructor(
    @InjectRepository(Notification)
    private notificationRepository: Repository<Notification>,
  ) {}

  async findAll(): Promise<Notification[]> {
    return this.notificationRepository.find({
      order: { date: 'DESC' }
    });
  }

  async findByUserId(userId: number): Promise<Notification[]> {
    return this.notificationRepository.find({
      where: { userId },
      order: { date: 'DESC' }
    });
  }

  async findUnreadByUserId(userId: number): Promise<Notification[]> {
    return this.notificationRepository.find({
      where: { userId, read: false },
      order: { date: 'DESC' }
    });
  }

  async findOne(id: number, userId: number): Promise<Notification> {
    const notification = await this.notificationRepository.findOne({ 
      where: { id, userId } 
    });
    
    if (!notification) {
      throw new NotFoundException(`Notification with ID ${id} not found`);
    }
    
    return notification;
  }

  async create(userId: number, createNotificationDto: CreateNotificationDto): Promise<Notification> {
    const notification = this.notificationRepository.create({
      ...createNotificationDto,
      userId
    });
    
    return this.notificationRepository.save(notification);
  }

  async update(id: number, userId: number, updateNotificationDto: UpdateNotificationDto): Promise<Notification> {
    const notification = await this.findOne(id, userId);
    
    Object.assign(notification, updateNotificationDto);
    
    return this.notificationRepository.save(notification);
  }

  async markAsRead(id: number, userId: number): Promise<Notification> {
    const notification = await this.findOne(id, userId);
    notification.read = true;
    
    return this.notificationRepository.save(notification);
  }

  async markAllAsRead(userId: number): Promise<void> {
    await this.notificationRepository.update(
      { userId, read: false },
      { read: true }
    );
  }

  async remove(id: number, userId: number): Promise<void> {
    const notification = await this.findOne(id, userId);
    await this.notificationRepository.remove(notification);
  }

  async getUnreadCount(userId: number): Promise<number> {
    const count = await this.notificationRepository.count({
      where: { userId, read: false }
    });
    
    return count;
  }
} 