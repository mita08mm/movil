import { Entity, Column, PrimaryGeneratedColumn, ManyToOne, JoinColumn } from 'typeorm';
import { User } from '../../../model/user/user.entity';

@Entity('notifications')
export class Notification {
  @PrimaryGeneratedColumn('identity', { type: 'bigint' })
  id: number;

  @Column({ type: 'int', name: 'user_id' })
  userId: number;

  @ManyToOne(() => User, user => user.notifications, { onDelete: 'CASCADE' })
  @JoinColumn({ name: 'user_id' })
  user: User;

  @Column()
  message: string;

  @Column({ default: false })
  read: boolean;

  @Column({ name: 'date', type: 'timestamptz', default: () => 'now()' })
  date: Date;
} 