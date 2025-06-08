import { Entity, Column, PrimaryGeneratedColumn, OneToMany } from 'typeorm';
import { Note } from '../../modules/notes/entities/note.entity';
import { Task } from '../../modules/tasks/entities/task.entity';
import { Notification } from '../../modules/notifications/entities/notification.entity';

@Entity('users')
export class User {
  @PrimaryGeneratedColumn('identity', { type: 'bigint' })
  id: string;

  @Column()
  name: string;

  @Column({ unique: true })
  email: string;

  @Column({ name: 'password' })
  passwordHash: string;

  @OneToMany(() => Note, note => note.user)
  notes: Note[];

  @OneToMany(() => Task, task => task.user)
  tasks: Task[];

  @OneToMany(() => Notification, notification => notification.user)
  notifications: Notification[];
}
