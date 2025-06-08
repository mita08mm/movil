import { Entity, Column, PrimaryGeneratedColumn, ManyToOne, JoinColumn, OneToMany } from 'typeorm';
import { Note } from './note.entity';

@Entity('categories')
export class Category {
  @PrimaryGeneratedColumn('identity', { type: 'bigint' })
  id: string;

  @Column()
  name: string;

  @Column({ nullable: true })
  description: string;

  @Column({ nullable: true, name: 'parent_category_id', type: 'bigint' })
  parentCategoryId: string;

  @ManyToOne(() => Category, category => category.childCategories, { onDelete: 'CASCADE', nullable: true })
  @JoinColumn({ name: 'parent_category_id' })
  parentCategory: Category;

  @OneToMany(() => Category, category => category.parentCategory)
  childCategories: Category[];

  @OneToMany(() => Note, note => note.category)
  notes: Note[];
}
