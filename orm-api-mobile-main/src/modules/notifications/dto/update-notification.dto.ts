import { IsString, IsOptional, IsBoolean } from 'class-validator';

export class UpdateNotificationDto {
  @IsOptional()
  @IsString()
  message?: string;
  
  @IsOptional()
  @IsBoolean()
  read?: boolean;
} 