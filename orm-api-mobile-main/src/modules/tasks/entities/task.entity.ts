import { Entity, Column, PrimaryGeneratedColumn, ManyToOne, JoinColumn } from 'typeorm';
import { User } from '../../../model/user/user.entity';
import { TaskStatus } from '../task-status.enum';

@Entity('tasks')
export class Task {
  @PrimaryGeneratedColumn('identity', { type: 'bigint' })
  id: number;

  @Column({ type: 'int', name: 'user_id' })
  userId: number;

  @ManyToOne(() => User, user => user.tasks, { onDelete: 'CASCADE' })
  @JoinColumn({ name: 'user_id' })
  user: User;

  @Column()
  title: string;

  @Column({ nullable: true })
  description: string;

  @Column({ name: 'creation_date', type: 'timestamptz', default: () => 'now()' })
  creationDate: Date;

  @Column({ name: 'due_date', nullable: true, type: 'timestamptz' })
  dueDate: Date;

  @Column({
    name: 'status',
    type: 'enum',
    enum: TaskStatus,
    default: TaskStatus.PENDING
  })
  status: TaskStatus;
} 