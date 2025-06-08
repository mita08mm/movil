import { IsNotEmpty, IsString, IsOptional, IsBoolean } from 'class-validator';

export class CreateNotificationDto {
  @IsNotEmpty()
  @IsString()
  message: string;
  
  @IsOptional()
  @IsBoolean()
  read?: boolean;
} 